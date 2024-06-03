package com.service;


import com.dto.CredentialsDTO;
import com.dto.SignUpDTO;
import com.dto.UserDTO;
import com.exception.AuthException;
import com.exception.UserException;
import com.mapper.UserMapper;
import com.model.User;
import com.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        System.out.println("userId =" + userId);
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("user not found with userId - " + userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt){return null;}

    @Override
    public User findUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);

        if(user != null){
            return user;
        }throw new UserException("User Profile not found with email" + email);
    }

    @Override
    public User loadUserByUsername(String username) {
        return null;
    }

    @Override
    public UserDTO login(CredentialsDTO credentialsDTO) throws AuthException {

        User user = userRepository.findByUserName(credentialsDTO.userName())
                .orElseThrow(() -> new AuthException("Unknown User", HttpStatus.NOT_FOUND));
        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.password()),user.getPassword())) {
            return userMapper.toUserDto(user);
        }throw new AuthException("Invalid password", HttpStatus.BAD_REQUEST);
    }


    @Override
    public UserDTO register(SignUpDTO signUpDto) {

        System.out.println("signUpDto "+ signUpDto.userName());
        System.out.println("signUpDto "+ Arrays.toString(signUpDto.password()));
        System.out.println("signUpDto "+ signUpDto.email());

        Optional<User> oUser = userRepository.findByUserName(signUpDto.userName());

        if(oUser.isPresent()){
            throw new AuthException("Username already Exists", HttpStatus.BAD_REQUEST);
            //TO DO Add Exception for Existing Email address too
        }
        User user = userMapper.signUpToUser(signUpDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    //    @Override
//    public User findUserProfileByJwt(String jwt) throws UserException {
////        String email = jwtProvider.getEmailFromToken(jwt);
//        User user = userRepository.findByEmail(email);
//        if(user != null){
//            return user;
//        }
//        throw new UserException("User Profile not found with email" + email);
//    }


}

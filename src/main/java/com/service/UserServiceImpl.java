package com.service;

import com.dto.CredentialsDTO;
import com.dto.SignUpDTO;
import com.dto.UserDTO;
import com.exception.AuthException;
import com.exception.UserException;
import com.mapper.UserMapper;
import com.model.User;
import com.repository.UserRepository;
import com.response.UserAuthProvider;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserAuthProvider userAuthProvider;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User findUserById(Long userId) throws UserException {
        System.out.println("userId =" + userId);
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("user not found with userId - " + userId);
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return user;
        }
        throw new UserException("User Profile not found with email" + email);
    }

    @Override
    public User loadUserByUsername(String username) {
        return null;
    }

    @Override
    public UserDTO login(CredentialsDTO credentialsDTO) throws AuthException {
        logger.debug("Credentials", credentialsDTO);

        User user = userRepository.findByUserName(credentialsDTO.userName())
                .orElseThrow(() -> new AuthException("Unknown User", HttpStatus.NOT_FOUND));
        logger.debug("UserEntity", user);
        logger.debug("UserEntity Username", user.getUserName());
        logger.debug("UserEntity password", user.getPassword());
        return userMapper.toUserDto(user);
        // if (passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.password()),
        // user.getPassword())) {}
    }

    @Override
    public UserDTO register(SignUpDTO signUpDto) throws AuthException {

        logger.debug("SignupDTO class", signUpDto);
        Optional<User> oUser = userRepository.findByUserName(signUpDto.userName());

        if (oUser.isPresent()) {
            throw new AuthException("Username already Exists", HttpStatus.BAD_REQUEST);
            // TO DO Add Exception for Existing Email address too
        }
        User user = userMapper.signUpDTOToUser(signUpDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    @Override
    public UserDTO findUserProfileByJwt(String jwt) throws UserException {
        String userName = userAuthProvider.getUserNameFromToken(jwt);
        Optional<User> user = userRepository.findByUserName(userName);
        if (user != null) {
            return userMapper.toUserDto(user.get());
        }
        throw new UserException("User Profile not found with User Name" + userName);
    }

}

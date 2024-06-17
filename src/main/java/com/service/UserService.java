package com.service;


import com.dto.CredentialsDTO;
import com.dto.SignUpDTO;
import com.exception.AuthException;
import com.exception.UserException;
import com.model.User;
import com.dto.UserDTO;


import org.springframework.stereotype.Service;

//import org.springframework.security.core.userdetails.UserDetails;
@Service
public interface UserService {

    User findUserById(Long userId) throws UserException;

    User findUserProfileByJwt(String jwt) throws UserException;

    User findUserByEmail(String email) throws UserException;

    User loadUserByUsername(String username);

    UserDTO login(CredentialsDTO credentialsDTO) throws AuthException;

    UserDTO register(SignUpDTO signUpDto);
}

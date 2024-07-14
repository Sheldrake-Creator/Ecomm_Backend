package com.service;

import com.dto.CredentialsDTO;
import com.dto.SignUpDTO;
import com.exception.AuthException;
import com.exception.UserServiceException;
import com.model.User;
import com.dto.UserDTO;

import org.springframework.stereotype.Service;

//import org.springframework.security.core.userdetails.UserDetails;
@Service
public interface UserService {

    UserDTO findUserById(Long userId) throws UserServiceException;

    UserDTO findUserProfileByJwt(String jwt) throws UserServiceException;

    Long getUserIdByJwt(String jwt) throws UserServiceException;

    User findUserByEmail(String email) throws UserServiceException;

    User loadUserByUsername(String username);

    UserDTO login(CredentialsDTO credentialsDTO) throws AuthException;

    UserDTO register(SignUpDTO signUpDto) throws AuthException;
}

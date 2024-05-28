package com.service;


import com.exception.UserException;
import com.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

    public User  loadUserByUsername(String username);
}

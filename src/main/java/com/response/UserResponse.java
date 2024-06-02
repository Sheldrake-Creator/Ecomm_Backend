package com.response;

import com.dto.UserDTO;

public class UserResponse implements AuthResponse {

    private UserDTO user;

    public UserResponse(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}


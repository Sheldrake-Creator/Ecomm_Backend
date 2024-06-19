package com.controller;

import com.exception.UserException;
import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("GrazieInspection")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // @GetMapping("/profile")
    // public ResponseEntity<User>
    // getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws
    // UserException{
    //
    // User user = userService.findUserProfileByJwt(jwt);
    // return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
    // }
    @PostMapping("/id")
    public ResponseEntity<User> getUserById(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        Long id = user.getUserId();
        String name = user.getUserName();

        System.out.println("User firstname must not be null " + name);
        System.out.println("User firstname must not be null " + email);
        System.out.println("id from request: " + id);

        User profile = userService.findUserByEmail(email);

        if (profile == null) {
            throw new UserException("User email not found");
        }

        return new ResponseEntity<>(profile, HttpStatus.ACCEPTED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> hi() {
        return ResponseEntity.ok("This is working!");

    }

}

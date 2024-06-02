package com.controller;

//import com.service.CustomerUserServiceImpl;

//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import com.dto.CredentialsDTO;
import com.dto.SignUpDTO;
import com.dto.UserDTO;

import com.exception.UserException;
import com.response.AuthResponse;
import com.response.UserResponse;
import com.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthController {
//
//    private UserRepository userRepository;
//    private JwtProvider jwtProvider;
//    private PasswordEncoder passwordEncoder;
//    private CustomerUserServiceImpl customerUserService;
//    private CartService cartService;
private  UserService userService;

public AuthController(UserService userService){
    this.userService = userService;
}

//
//
//    public AuthController(UserRepository userRepository, JwtProvider jwtProvider,
//                          PasswordEncoder passwordEncoder,CustomerUserServiceImpl customerUserService, CartService cartService) {
//        this.userRepository = userRepository;
//        this.jwtProvider = jwtProvider;
//        this.passwordEncoder = passwordEncoder;
//        this.customerUserService = customerUserService;
//        this.cartService=cartService;
//    }
//

    @PostMapping("/login")
    public ResponseEntity<UserDTO>loginHandler(@RequestBody CredentialsDTO credentialsDto)throws UserException {
        UserDTO user = userService.login(credentialsDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse>registerHandler(@RequestBody SignUpDTO signUpDto)throws UserException {
        UserDTO userDTO = userService.register(signUpDto);
        return ResponseEntity.ok(new UserResponse(userDTO));
    }





//    @PostMapping("/signin")
//    public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest){
//
//        String username= loginRequest.getEmail();
//        String password = loginRequest.getPassword();
//
//        Authentication authentication = authenticate(username,password);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String token = jwtProvider.generateToken(authentication);
//
//        AuthResponse authResponse = new AuthResponse();
//        authResponse.setJwt(token);
//        authResponse.setMessage("Signin success");
//
//        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
//    }
//
//    private Authentication authenticate(String username,String password){
//        UserDetails userDetails = customerUserService.loadUserByUsername(username);
//
//        if(userDetails == null){
//            throw new BadCredentialsException("password invalid...");
//        }
//        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
//            throw new BadCredentialsException("Invalid Password...");
//        }
//            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//    }
//
//
//
//
}

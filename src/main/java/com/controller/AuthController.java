package com.controller;

import com.dto.CredentialsDTO;
import com.dto.SignUpDTO;
import com.dto.UserDTO;
import com.exception.AuthException;
import com.response.HttpResponse;
import com.response.UserAuthProvider;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@CrossOrigin(origins = "http://silas-ecomm.com")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuth;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> loginHandler(@RequestBody CredentialsDTO credentialsDto) {
        try {
            logger.debug("credentialsDto: ", credentialsDto);
            UserDTO userDTO = userService.login(credentialsDto);
            logger.debug("userdDto", userDTO);
            userDTO.setToken(userAuth.createToken(userDTO));
            logger.debug("User logged in: {}", userDTO);
            return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                    .data(Map.of("user", userDTO)).message("Login successful").status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value()).build());
        } catch (AuthException e) {
            logger.error("Error during login", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString()).message("Error during login")
                            .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
        } catch (Exception e) {
            logger.error("Unexpected error during login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Unexpected error during login").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> registerHandler(@RequestBody SignUpDTO signUpDto) {
        try {
            UserDTO userDTO = userService.register(signUpDto);
            userDTO.setToken(userAuth.createToken(userDTO));
            logger.debug("User registered: {}", userDTO);
            return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                    .data(Map.of("user", userDTO)).message("Registration successful").status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value()).build());
        } catch (AuthException e) {
            logger.error("Error during registration", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Error during registration").status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value()).build());
        } catch (Exception e) {
            logger.error("Unexpected error during registration", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Unexpected error during registration").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }
}

// @PostMapping("/signin")
// public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest
// loginRequest){
//
// String username= loginRequest.getEmail();
// String password = loginRequest.getPassword();
//
// Authentication authentication = authenticate(username,password);
// SecurityContextHolder.getContext().setAuthentication(authentication);
//
// String token = jwtProvider.generateToken(authentication);
//
// AuthResponse authResponse = new AuthResponse();
// authResponse.setJwt(token);
// authResponse.setMessage("Signin success");
//
// return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
// }
//
// private Authentication authenticate(String username,String password){
// UserDetails userDetails = customerUserService.loadUserByUsername(username);
//
// if(userDetails == null){
// throw new BadCredentialsException("password invalid...");
// }
// if(!passwordEncoder.matches(password, userDetails.getPassword())) {
// throw new BadCredentialsException("Invalid Password...");
// }
// return new UsernamePasswordAuthenticationToken(userDetails, null,
// userDetails.getAuthorities());
// }
//
//
//
//
// }

package com.controller;

import com.dto.UserDTO;
import com.exception.UserException;
import com.model.User;
import com.response.HttpResponse;
import com.service.CartItemService;
import com.service.TestService;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TestService testService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/id")
    public ResponseEntity<HttpResponse> getUserById(@RequestHeader("Authorization") String jwt) {
        try {
            UserDTO userDTO = userService.findUserProfileByJwt(jwt);
            if (userDTO == null) {
                throw new UserException("Authorization Issue is Occuring");
            }

            logger.debug("UserName: {}", userDTO.getUserName());

            UserDTO profile = userService.findUserById(userDTO.getUserId());

            if (profile == null) {
                throw new UserException("User UserId not found");
            }

            logger.debug("User profile found: {}", profile.getUserName());

            return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                    .data(Map.of("user", profile)).message("User profile fetched successfully").status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value()).build());
        } catch (UserException e) {
            logger.error("Error fetching user profile", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString()).message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
        } catch (Exception e) {
            logger.error("Unexpected error fetching user profile", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Unexpected error fetching user profile").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<HttpResponse> hi(@RequestHeader("Authorization") String jwt) {
        try {


            this.testService.testEverything(jwt);



            return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                    .data(Map.of("message", "This is working!")).message("Test endpoint").status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value()).build());
        } catch (Exception e) {
            logger.error("Unexpected error in test endpoint", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Unexpected error in test endpoint").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }
}

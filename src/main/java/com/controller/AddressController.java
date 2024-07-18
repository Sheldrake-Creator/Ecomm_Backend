package com.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.AddressDTO;
import com.exception.UserServiceException;
import com.response.HttpResponse;
import com.service.AddressService;
import com.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

        private final AddressService addressService;
        private final UserService userService;
        private final Logger logger = LoggerFactory.getLogger(AddressController.class);

        @PostMapping("/add")
        public ResponseEntity<HttpResponse> addAddress(@RequestBody AddressDTO shippingAddress,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        Long userId = this.userService.getUserIdByJwt(jwt);
                        AddressDTO address = this.addressService.addAddress(userId, shippingAddress);
                        logger.debug("Address Added");
                        return ResponseEntity.status(HttpStatus.CREATED).body(HttpResponse.builder()
                                        .timeStamp(LocalDateTime.now().toString()).data(Map.of("address", address))
                                        .message("Address Added to User Profile").status(HttpStatus.CREATED)
                                        .statusCode(HttpStatus.CREATED.value()).build());
                } catch (UserServiceException e) {
                        logger.error("Error creating order: User exception", e);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Error creating order: User exception")
                                                        .status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                } catch (Exception e) {
                        logger.error("Unexpected error creating order", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Unexpected error creating order")
                                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
                }
        }

        @PutMapping("/update")
        public ResponseEntity<HttpResponse> updateAddress(@RequestBody AddressDTO shippingAddress,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        Long userId = this.userService.getUserIdByJwt(jwt);
                        AddressDTO address = this.addressService.updateAddress(userId, shippingAddress);
                        logger.debug("Address Updated");
                        return ResponseEntity.status(HttpStatus.CREATED).body(HttpResponse.builder()
                                        .timeStamp(LocalDateTime.now().toString()).data(Map.of("address", address))
                                        .message("Address Added to User Profile").status(HttpStatus.CREATED)
                                        .statusCode(HttpStatus.CREATED.value()).build());
                } catch (UserServiceException e) {
                        logger.error("Error creating order: User exception", e);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Error creating order: User exception")
                                                        .status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                } catch (Exception e) {
                        logger.error("Unexpected error creating order", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Unexpected error creating order")
                                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
                }
        }

        @DeleteMapping("/delete/{addressId}")
        public ResponseEntity<HttpResponse> deleteAddress(@RequestBody AddressDTO shippingAddress,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        Long userId = this.userService.getUserIdByJwt(jwt);
                        AddressDTO address = this.addressService.removeAddress(userId, shippingAddress);
                        logger.debug("Address Removed");
                        return ResponseEntity.status(HttpStatus.CREATED).body(HttpResponse.builder()
                                        .timeStamp(LocalDateTime.now().toString()).data(Map.of("address", address))
                                        .message("Address Added to User Profile").status(HttpStatus.CREATED)
                                        .statusCode(HttpStatus.CREATED.value()).build());
                } catch (UserServiceException e) {
                        logger.error("Error creating order: User exception", e);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Error creating order: User exception")
                                                        .status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                } catch (Exception e) {
                        logger.error("Unexpected error creating order", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Unexpected error creating order")
                                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
                }
        }

}

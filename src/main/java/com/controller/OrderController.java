package com.controller;

import com.dto.AddressDTO;
import com.dto.CartDTO;
import com.dto.OrderDTO;
import com.dto.UserDTO;
import com.exception.OrderServiceException;
import com.exception.UserServiceException;
import com.response.HttpResponse;
import com.service.OrderService;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

        private final OrderService orderService;
        private final UserService userService;
        private final Logger logger = LoggerFactory.getLogger(OrderController.class);

        @GetMapping("/")
        public ResponseEntity<HttpResponse> createOrder(@RequestHeader("Authorization") String jwt, CartDTO cart,
                        AddressDTO address) {
                try {
                        UserDTO user = userService.findUserProfileByJwt(jwt);
                        OrderDTO order = orderService.createOrder(user, address);
                        logger.debug("Order created: {}", order);
                        return ResponseEntity.status(HttpStatus.CREATED)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .data(Map.of("order", order)).message("Order created")
                                                        .status(HttpStatus.CREATED)
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

        @GetMapping("/{orderId}")
        public ResponseEntity<HttpResponse> findOrderById(@PathVariable long orderId,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        logger.debug("OrderID: {}" + orderId);
                        logger.debug("Token; {}" + jwt);
                        OrderDTO order = orderService.findOrderById(orderId);
                        logger.debug("Order retrieved: {}", order);
                        return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                        .data(Map.of("order", order)).message("Order retrieved").status(HttpStatus.OK)
                                        .statusCode(HttpStatus.OK.value()).build());
                } catch (OrderServiceException e) {
                        logger.error("Error retrieving order: Order exception", e);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Error retrieving order: Order exception")
                                                        .status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                } catch (Exception e) {
                        logger.error("Unexpected error retrieving order", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Unexpected error retrieving order")
                                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
                }
        }

        @GetMapping("/user")
        public ResponseEntity<HttpResponse> usersOrderHistory(@RequestHeader("Authorization") String jwt) {
                try {
                        UserDTO user = userService.findUserProfileByJwt(jwt);
                        List<OrderDTO> orders = orderService.usersOrderHistory(user.getUserId());
                        logger.debug("User order history retrieved: [] {}", orders);
                        return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                        .data(Map.of("orders", orders)).message("User order history retrieved")
                                        .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
                } catch (UserServiceException e) {
                        logger.error("Error retrieving order history: User exception", e);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Error retrieving order history: User exception")
                                                        .status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                } catch (OrderServiceException e) {
                        logger.error("Error retrieving order history: Order exception", e);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Error retrieving order history: Order exception")
                                                        .status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                } catch (Exception e) {
                        logger.error("Unexpected error retrieving order history", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Unexpected error retrieving order history")
                                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
                }
        }
}

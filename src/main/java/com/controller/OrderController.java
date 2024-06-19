package com.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.AddressDTO;
import com.dto.OrderDTO;
import com.dto.UserDTO;
import com.exception.OrderException;
import com.exception.UserException;
import com.service.OrderService;
import com.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {


    private OrderService orderService;
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody AddressDTO shippingAddress,
                                             @RequestHeader("Authorization") String jwt) throws UserException {
        UserDTO user = userService.findUserProfileByJwt(jwt);
        OrderDTO order = orderService.createOrder(user, shippingAddress);

        return new ResponseEntity<OrderDTO>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDTO>>usersOrderHistory(
            @RequestHeader(" Authorization") String jwt) throws UserException {
        UserDTO user = userService.findUserProfileByJwt(jwt);
        List<OrderDTO> orders = orderService.usersOrderHistory(user.getUserId());
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<OrderDTO> findOrderById(
            @PathVariable("Id") Long orderId,
            @RequestHeader("Authorization") String jwt) throws UserException, OrderException {
        // User user =userService.findUserProfileByJwt(jwt);
        OrderDTO order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }


}

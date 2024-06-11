package com.controller;
import com.dto.CartDTO;
import com.dto.UserDTO;
import com.exception.UserException;
import com.model.Cart;
import com.model.User;
import com.request.CartRequest;
import com.response.CartResponse;
import com.service.CartService;
import com.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Tag(name="Cart Management",description= "find user cart, add item to cart")
@AllArgsConstructor
public class CartController {


    private CartService cartService;
    private UserService userService;


    @GetMapping("/")
    @Operation(description = "find cart by user id")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization")String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getUserId());
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PostMapping(value="api/createCart")
    public ResponseEntity<CartResponse> createCart(@RequestBody CartRequest cartRequest)throws UserException {
        UserDTO userDTO =cartRequest.getUser();
        System.out.println("UserDTO "+userDTO);
        System.out.println("UserDTO "+userDTO.getUserId());
        CartDTO cart = cartService.createCart(userDTO);
        return ResponseEntity.ok(new CartResponse(cart));
    }
}

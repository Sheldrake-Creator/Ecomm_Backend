package com.controller;
import com.dto.CartDTO;
import com.dto.UserDTO;
import com.exception.UserException;
import com.model.Cart;
import com.model.User;
import com.response.CartResponse;
import com.service.CartService;
import com.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Tag(name="Cart Management",description= "find user cart, add item to cart")
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
    public ResponseEntity<CartResponse> createCart(UserDTO user)throws UserException {
        CartDTO cart = cartService.createCart(user);
        return ResponseEntity.ok(new CartResponse(cart));
    }
}

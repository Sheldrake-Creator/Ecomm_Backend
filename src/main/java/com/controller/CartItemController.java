package com.controller;

import com.dto.CartItemDTO;
import com.exception.CartItemException;
import com.exception.ProductException;
import com.exception.UserException;
import com.model.CartItem;
import com.model.User;
import com.request.AddItemRequest;
import com.response.APIResponse;
import com.service.CartItemService;
import com.service.CartService;
import com.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
@AllArgsConstructor
public class CartItemController {

    UserService userService;
    CartItemService cartItemService;
    CartService cartService;


    @PutMapping("/add")
    @Operation(description = "add item to cart")
    public ResponseEntity<APIResponse>addItemToCart(@RequestHeader("Authorization") String jwt, @RequestBody AddItemRequest req) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();


        String message =this.cartService.addItemToCart(userId, req.getQuantity(), req.getSize(), req.getProductId());
        System.out.println("Message: " + message);

        APIResponse res=new APIResponse();
        res.setMessage(message);
        res.setStatus(true);
        return new ResponseEntity<>(res,HttpStatus.OK);

        // @RequestHeader("Authorization")String jwt       
    }

    @DeleteMapping("/{cartItemId}")
    @Operation(description = "Remove Cart Item From Cart")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(description="Delete Item")
            public ResponseEntity<APIResponse> deleteCartItem(@PathVariable Long cartItemId,
                                                              @RequestHeader("Authorization") String jwt) throws UserException, CartItemException{
        User user=userService.findUserProfileByJwt(jwt);

        cartItemService.removeCartItem(user.getUserId(), cartItemId);

        APIResponse res=new APIResponse();
        res.setMessage("delete item from cart");
        res.setStatus(true);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    @Operation(description = "Update Item To Cart")
    public ResponseEntity<CartItemDTO> updateCartItem(
            @RequestBody CartItemDTO cartItem,
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user =userService.findUserProfileByJwt(jwt);
        CartItemDTO updatedCartItem = cartItemService.updateCartItem(user.getUserId(), cartItemId, cartItem);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }
}

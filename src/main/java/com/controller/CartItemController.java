package com.controller;

import com.exception.CartItemException;
import com.exception.UserException;
import com.model.CartItem;
import com.model.User;
import com.response.APIResponse;
import com.service.CartItemService;
import com.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
public class CartItemController {

    UserService userService;
    CartItemService cartItemService;

    public CartItemController() {
    }

    public CartItemController(UserService userService, CartItemService cartItemService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
    }

    @DeleteMapping("/{cartItemId}")
    @Operation(description = "Remove Cart Item From Cart")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(description="Delete Item")
            public ResponseEntity<APIResponse> deleteCartItem(@PathVariable Long cartItemId,
                                                              @RequestHeader("Authorization") String jwt) throws UserException, CartItemException{
        User user=userService. findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        APIResponse res=new APIResponse();
        res.setMessage("delete item from cart");
        res.setStatus(true);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    @Operation(description = "Update Item To Cart")
    public ResponseEntity<CartItem> updateCartItem(
            @RequestBody CartItem cartItem,
            @PathVariable Long cartItemId,
            @RequestHeader(" Authorization") String jwt) throws UserException, CartItemException {
        User user =userService.findUserProfileByJwt(jwt);
        CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }
}

package com.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CartItemDTO;
import com.dto.UserDTO;
import com.exception.CartException;
import com.exception.CartItemException;
import com.exception.ProductException;
import com.exception.UserException;
import com.request.AddItemRequest;
import com.response.HttpResponse;
import com.service.CartItemService;
import com.service.CartService;
import com.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/item")
@AllArgsConstructor
public class CartItemController {

    UserService userService;
    CartItemService cartItemService;
    CartService cartService;

    @PutMapping("/add")
    @Operation(description = "add item to cart")
    public ResponseEntity<HttpResponse> addItemToCart(@RequestHeader("Authorization") String jwt,
            @RequestBody AddItemRequest req) throws UserException, ProductException, CartException, CartItemException {

        try {
            UserDTO user = userService.findUserProfileByJwt(jwt);
            Long userId = user.getUserId();
            String data = this.cartService.addItemToCart(userId, req.getQuantity(), req.getSize(),
                    req.getProductId());

            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .message("Item Successfully Added to cart")
                            .data(Map.of("cartItem",data))
                            .status(HttpStatus.OK)
                            .statusCode(200)
                            .build());
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .message("User not found")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .message("User Not found")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .build());
        }
    }

    @DeleteMapping("/{cartItemId}")
    @Operation(description = "Remove Cart Item From Cart")
    public ResponseEntity<HttpResponse> deleteCartItem(@PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {

        UserDTO user = userService.findUserProfileByJwt(jwt);

        cartItemService.removeCartItem(user.getUserId(), cartItemId);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("cartItem","Item Removed from cart"))
                .message("CartItem Removed from Cart")
                .status(HttpStatus.OK)
                .statusCode(200)
                .build());
    }

    @PutMapping("/{cartItemId}")
    @Operation(description = "Update Item To Cart")
    public ResponseEntity<HttpResponse> updateCartItem(
            @RequestBody CartItemDTO cartItem,
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {

        UserDTO user = userService.findUserProfileByJwt(jwt);
        CartItemDTO updatedCartItem = cartItemService.updateCartItem(user.getUserId(), cartItemId, cartItem);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
                .timeStamp(jwt)
                .message(jwt)
                .status(null)
                .statusCode(200)
                .data(Map.of("cartItem",updatedCartItem))
                .build());
    }
}

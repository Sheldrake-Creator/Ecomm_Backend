package com.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CartDTO;
import com.dto.CartItemDTO;
import com.exception.CartItemException;
import com.exception.UserException;
import com.request.AddItemRequest;
import com.response.HttpResponse;
import com.service.CartItemService;
import com.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class CartItemController {

        private final UserService userService;
        private final CartItemService cartItemService;
        private final Logger logger = LoggerFactory.getLogger(CartItemController.class);

        @PutMapping("/add")
        @Operation(description = "add item to cart")
        public ResponseEntity<HttpResponse> addItemToCart(@RequestHeader("Authorization") String jwt,
                        @RequestBody AddItemRequest req) throws UserException, CartItemException {
                try {
                        Long userId = userService.getUserIdByJwt(jwt);

                        CartDTO data = this.cartItemService.addItemToCart(userId, req.getQuantity(), req.getSize(),
                                        req.getProductId());

                        return ResponseEntity.ok().body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                        .message("Item Successfully Added to cart").data(Map.of("Cart", data))
                                        .status(HttpStatus.OK).statusCode(200).build());
                } catch (UserException e) {
                        return ResponseEntity.badRequest()
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("User not found").status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());

                } catch (CartItemException e) {
                        return ResponseEntity.badRequest()
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Cart not found").status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                }
        }

        @DeleteMapping("/{cartItemId}")
        @Operation(description = "Remove Cart Item From Cart")
        public ResponseEntity<HttpResponse> deleteCartItem(@PathVariable Long cartItemId,
                        @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {

                Long userId = userService.getUserIdByJwt(jwt);
                CartItemDTO cartItem = new CartItemDTO();
                cartItem.setCartItemId(cartItemId);

                cartItemService.removeCartItem(userId, cartItemId);
                return ResponseEntity.ok().body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                .data(Map.of("cartItem", cartItem)).message("CartItem Removed from Cart")
                                .status(HttpStatus.OK).statusCode(200).build());
        }

        @PutMapping("/{cartItemId}")
        @Operation(description = "Update Item To Cart")
        public ResponseEntity<HttpResponse> updateCartItem(@RequestBody CartItemDTO cartItem,
                        @PathVariable Long cartItemId) throws UserException, CartItemException {
                try {
                        logger.debug("Req CartItemDTO: {}", cartItem);

                        logger.debug("Req cartItemId: {}", cartItemId);
                        CartItemDTO updatedCartItem = cartItemService.updateCartItem(cartItemId, cartItem);
                        return ResponseEntity.ok().body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                        .message("Cart Item updated!").status(HttpStatus.OK).statusCode(200)
                                        .data(Map.of("cartItem", updatedCartItem)).build());
                } catch (CartItemException e) {
                        return ResponseEntity.badRequest()
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("User not found").status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                }
        }

}

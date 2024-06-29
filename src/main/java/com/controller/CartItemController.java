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
import com.dto.UserDTO;
import com.exception.CartException;
import com.exception.CartItemException;
import com.exception.ProductException;
import com.exception.UserException;
import com.request.AddItemRequest;
import com.response.HttpResponse;
import com.service.CartItemService;
import com.service.UserService;
import com.util.LogUtils;

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
                LogUtils.entry();
                logger.debug("Entering addItemToCart()");
                try {
                        UserDTO user = userService.findUserProfileByJwt(jwt);

                        CartDTO data = this.cartItemService.addItemToCart(user, req.getQuantity(), req.getSize(),
                                        req.getProductId());

                        return ResponseEntity.ok().body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                        .message("Item Successfully Added to cart").data(Map.of("cartItem", data))
                                        .status(HttpStatus.OK).statusCode(200).build());
                } catch (UserException e) {
                        return ResponseEntity.badRequest()
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("User not found").status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                } catch (ProductException e) {
                        return ResponseEntity.badRequest()
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("User not found").status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                } catch (CartException e) {
                        return ResponseEntity.badRequest()
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("User not found").status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                }
        }

        @DeleteMapping("/{cartItemId}")
        @Operation(description = "Remove Cart Item From Cart")
        public ResponseEntity<HttpResponse> deleteCartItem(@PathVariable Long cartItemId,
                        @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {

                UserDTO user = userService.findUserProfileByJwt(jwt);

                cartItemService.removeCartItem(user.getUserId(), cartItemId);
                return ResponseEntity.ok().body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                .data(Map.of("cartItem", "Item Removed from cart"))
                                .message("CartItem Removed from Cart").status(HttpStatus.OK).statusCode(200).build());
        }

        @PutMapping("/{cartItemId}")
        @Operation(description = "Update Item To Cart")
        public ResponseEntity<HttpResponse> updateCartItem(@RequestBody CartItemDTO cartItem,
                        @PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt)
                        throws UserException, CartItemException {
                try {
                        UserDTO user = userService.findUserProfileByJwt(jwt);
                        CartItemDTO updatedCartItem = cartItemService.updateCartItem(user.getUserId(), cartItem);
                        return ResponseEntity.ok().body(HttpResponse.builder().timeStamp(jwt).message(jwt).status(null)
                                        .statusCode(200).data(Map.of("cartItem", updatedCartItem)).build());
                } catch (CartItemException | UserException | CartException e) {
                        return ResponseEntity.badRequest()
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("User not found").status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                }
        }

}

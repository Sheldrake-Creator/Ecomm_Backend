package com.controller;

import com.dto.CartDTO;

import com.exception.CartServiceException;
import com.exception.UserServiceException;
import com.response.HttpResponse;
import com.service.CartService;
import com.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cart Management", description = "find user cart, add item to cart")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://silas-ecomm.com")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    @GetMapping(value = "/getCart")
    @Operation(description = "find cart by user id")
    public ResponseEntity<HttpResponse> getUserCart(@RequestHeader("Authorization") String jwt) {
        try {
            logger.debug("TOKEN: {} " + jwt);
            Long userId = userService.getUserIdByJwt(jwt);
            logger.debug("UserId: {} ", userId);
            CartDTO cartDto = cartService.getUserCart(userId);
            logger.debug("CART: {} ", cartDto);

            return ResponseEntity.ok().body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                    .data(Map.of("cart", cartDto)).message("Cart Found").status(HttpStatus.OK).statusCode(200).build());
        } catch (CartServiceException e) {
            return ResponseEntity.badRequest()
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString()).message("Cart not found")
                            .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString()).message("User Not found")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }

    // @PostMapping(value = "createCart")
    // public ResponseEntity<CartResponse> createCart(@RequestBody CartRequest
    // cartRequest) throws CartServiceException {
    // UserDTO userDTO = cartRequest.getUser();
    // System.out.println("CreateCartAction UserDTO " + userDTO);
    // System.out.println("CreateCartAction UserDTO " + userDTO.getUserId());
    // CartDTO cart = cartService.createCart(userDTO);
    // return ResponseEntity.ok(new CreateCartResponse(cart));
    // }
}

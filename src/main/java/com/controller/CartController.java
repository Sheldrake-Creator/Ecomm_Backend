package com.controller;
import com.dto.CartDTO;
import com.dto.UserDTO;
import com.exception.CartException;
import com.request.CartRequest;
import com.response.CartResponse;
import com.response.CreateCartResponse;
import com.response.GetCartResponse;
import com.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Cart Management",description= "find user cart, add item to cart")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class CartController {


    private CartService cartService;


    @PostMapping(value="/getCart")
    @Operation(description = "find cart by user id")
    public ResponseEntity<CartResponse> getUserCart(@RequestBody CartRequest cartRequest) throws CartException {        
        UserDTO userDTO =cartRequest.getUser();
        System.out.println("UserDTO "+userDTO);
        System.out.println("UserDTO "+userDTO.getUserId());
        CartDTO cart = cartService.getUserCart(userDTO);
        return ResponseEntity.ok(new GetCartResponse(cart));}
        

    @PostMapping(value="createCart")
    public ResponseEntity<CartResponse> createCart(@RequestBody CartRequest cartRequest)throws CartException{
        UserDTO userDTO =cartRequest.getUser();
        System.out.println("CreateCartAction UserDTO "+userDTO);
        System.out.println("CreateCartAction UserDTO "+userDTO.getUserId());
        CartDTO cart = cartService.createCart(userDTO);
        return ResponseEntity.ok(new CreateCartResponse(cart));
    }
}

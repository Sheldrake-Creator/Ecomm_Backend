package com.request;

import com.dto.CartItemDTO;
import com.dto.ProductDTO;
import com.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddItemRequest {

   // @Getter @Setter private UserDTO user;
   // @Getter @Setter private CartItemDTO cartItem;
   // @Getter @Setter private ProductDTO product;

    private Long productId;
    private String size;
    private int quantity;



}

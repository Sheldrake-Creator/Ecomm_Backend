package com.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

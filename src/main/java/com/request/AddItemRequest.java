package com.request;

import com.dto.CartItemDTO;
import com.dto.ProductDTO;
import com.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest {

   @Getter @Setter private UserDTO user;
   @Getter @Setter private CartItemDTO cartItem;
   @Getter @Setter private ProductDTO product;


    // private Long productId;
    // private String size;
    // private int quantity;
    // private Integer price;

    // public AddItemRequest() {
    // }

    // public AddItemRequest(Long productId, String size, int quantity, Integer price) {
    //     this.productId = productId;
    //     this.size = size;
    //     this.quantity = quantity;
    //     this.price = price;
    // }

    // public Long getProductId() {
    //     return productId;
    // }

    // public void setProductId(Long productId) {
    //     this.productId = productId;
    // }

    // public String getSize() {
    //     return size;
    // }

    // public void setSize(String size) {
    //     this.size = size;
    // }

    // public int getQuantity() {
    //     return quantity;
    // }

    // public void setQuantity(int quantity) {
    //     this.quantity = quantity;
    // }

    // public Integer getPrice() {
    //     return price;
    // }

    // public void setPrice(Integer price) {
    //     this.price = price;
    // }
}

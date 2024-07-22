
package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.CartDTO;
import com.model.Cart;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class })
public interface CartMapper {

    // Mapping from CartDTO to Cart
    @Mapping(target = "user.userId", source = "userId")
    @Mapping(target = "cartItems", source = "cartItems")
    Cart toCart(CartDTO cartDTO);

    // Reverse mapping from Cart to CartDTO
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "cartItems", source = "cartItems")
    CartDTO toCartDTO(Cart cart);
}
package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.CartItemDTO;
import com.model.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "cart.cartId", source = "cartId")

    CartItem toCartItem(CartItemDTO cartItemDTO);

    @Mapping(target = "cartId", source = "cart.cartId")

    CartItemDTO toCartItemDTO(CartItem createdCartItem);

}

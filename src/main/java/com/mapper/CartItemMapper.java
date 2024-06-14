package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.CartItemDTO;
import com.model.Cart;
import com.model.CartItem;
@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemDTO toCartItemDTO(Cart cart);


    @Mapping(target = "product", ignore = true)
    @Mapping(target = "cart", ignore = true)
    CartItem toCartItem(CartItemDTO cartItemDTO);
    
}

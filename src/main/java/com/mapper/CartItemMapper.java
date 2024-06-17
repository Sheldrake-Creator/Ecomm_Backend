package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.CartItemDTO;
import com.model.CartItem;
@Mapper(componentModel = "spring")
public interface CartItemMapper {
    
    CartItemDTO toCartItemDTO(CartItem createdCartItem);

    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "product", ignore = true)
    CartItem toCartItem(CartItemDTO cartItemDTO);
    
}

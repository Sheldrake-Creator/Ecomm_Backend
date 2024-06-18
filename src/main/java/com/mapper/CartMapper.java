
package com.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.CartDTO;
import com.model.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartDTO toCartDTO(Cart cart);
    

    @Mapping(target = "user", ignore = true)
    // @Mapping(target = "cartItems.cart", ignore = true)
    // @Mapping(target = "cartItems.product", ignore = true)
    Cart toCart(CartDTO cartDTO);
}


package com.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.dto.CartDTO;
import com.model.Cart;
import com.model.User;


@Mapper(componentModel = "spring")
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    // Mapping from CartDTO to Cart
    @Mapping(target = "user", source = "userId", qualifiedByName = "userIdToUser")
    Cart toCart(CartDTO cartDTO);

    @Named("userIdToUser")
    default User userIdToUser(Long userId) {
        User user = new User();
        user.setUserId(userId);
        return user;
    }
    // Reverse mapping from Cart to CartDTO
    @Mapping(target = "userId", source = "user.userId")
    CartDTO toCartDTO(Cart cart);
}
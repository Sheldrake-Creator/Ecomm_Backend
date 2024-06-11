
package com.mapper;
import org.mapstruct.Mapper;
import com.dto.CartDTO;
import com.model.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {


    String unmapped = null;

    CartDTO toCartDTO(Cart cart);

    Cart CartDTOtoCart(CartDTO cart);

}

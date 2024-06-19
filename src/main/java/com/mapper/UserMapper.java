package com.mapper;

import com.dto.SignUpDTO;
import com.dto.UserDTO;
import com.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "paymentInformation", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "token", ignore = true)
    User toUser(UserDTO userDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "paymentInformation", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "token", ignore = true)
    User signUpDTOToUser(SignUpDTO signUpDTO);

}

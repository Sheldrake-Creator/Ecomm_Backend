package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.OrderDTO;
import com.model.Order;

@Mapper(componentModel = "spring", uses = { OrderItemMapper.class, AddressMapper.class })
public interface OrderMapper {

    @Mapping(target = "orderItems", source = "orderItems")
    @Mapping(target = "userId", source = "user.userId")
    OrderDTO toOrderDTO(Order order);

    // @Mapping(target = "paymentDetails", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "orderItems", source = "orderItems")
    @Mapping(target = "user.userId", source = "userId")
    Order toOrder(OrderDTO orderDto);

}

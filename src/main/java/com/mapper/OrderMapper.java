package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.OrderDTO;
import com.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "address", source = "shippingAddress")
    OrderDTO toOrderDTO(Order order);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "shippingAddress", source = "address")
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "paymentDetails", ignore = true)
    Order toOrder(OrderDTO orderDto);


}

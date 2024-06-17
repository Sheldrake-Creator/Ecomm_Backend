package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.OrderItemDTO;
import com.model.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {


    OrderItemDTO toOrderItemDTO(OrderItem orderItem);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItem toOrder(OrderItemDTO orderItemDto);

}

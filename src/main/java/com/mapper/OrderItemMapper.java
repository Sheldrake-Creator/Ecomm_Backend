package com.mapper;

import org.mapstruct.Mapper;

import com.dto.OrderItemDTO;
import com.model.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemDTO toOrderItemDTO(OrderItem orderItem);

    OrderItem toOrder(OrderItemDTO orderItemDto );

}

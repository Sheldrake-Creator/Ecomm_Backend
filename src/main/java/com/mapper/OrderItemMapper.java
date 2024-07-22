package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.OrderItemDTO;
import com.model.OrderItem;

@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface OrderItemMapper {

    @Mapping(target = "product", source = "product")
    @Mapping(target = "orderId", source = "order.orderId")
    OrderItemDTO toOrderItemDTO(OrderItem orderItem);

    @Mapping(target = "product", source = "product")
    @Mapping(target = "order.orderId", source = "orderId")
    OrderItem toOrderItem(OrderItemDTO orderItemDto);

}

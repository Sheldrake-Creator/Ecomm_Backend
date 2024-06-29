package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.OrderItemDTO;
import com.model.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "orderId", source = "order.orderId")
    OrderItemDTO toOrderItemDTO(OrderItem orderItem);

    @Mapping(target = "product.productId", source = "productId")
    @Mapping(target = "order.orderId", source = "orderId")
    OrderItem toOrderItem(OrderItemDTO orderItemDto);

}

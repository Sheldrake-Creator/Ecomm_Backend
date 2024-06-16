package com.mapper;

import org.mapstruct.Mapper;

import com.dto.OrderDTO;
import com.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO toOrderDTO(Order order);

    Order toOrder(OrderDTO orderDto);

        //TODO: Add Annotations

}

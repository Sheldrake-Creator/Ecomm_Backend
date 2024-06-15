package com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {
    private Long orderItemId;
    private OrderDTO orderDto;
    private String size;
    private int quantity;
    private Integer orderPrice;
    private Integer discountedPrice;
    private Long userId;
}

package com.dto;

import java.time.LocalDateTime;

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
    private OrderDTO order;
    private ProductDTO product;
    private String size;
    private int quantity;
    private Integer orderPrice;
    private Integer discountedPrice;
    private UserDTO user;
    private LocalDateTime deliveryDate;
}

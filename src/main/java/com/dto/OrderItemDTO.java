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
    private Long orderId;
    private Long productId;
    private String size;
    private Integer quantity;
    private Integer orderPrice;
    private Integer discountedPrice;
    private LocalDateTime deliveryDate;
    // test
}

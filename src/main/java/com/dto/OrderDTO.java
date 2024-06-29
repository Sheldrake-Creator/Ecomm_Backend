package com.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    private Long orderId;
    private List<OrderItemDTO> orderItems;
    private AddressDTO shippingAddress;
    // private PaymentDetailsDTO PaymentDetails;
    private Integer totalPrice;
    private Integer totalDiscountedPrice;
    private Integer discount;
    private String orderStatus;
    private int totalItems;
    private LocalDateTime deliveryDate;
    private LocalDateTime orderDate;
    private Long userId;
}

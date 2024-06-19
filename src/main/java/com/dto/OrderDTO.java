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
public class OrderDTO {

    private long orderId;
    // private List<OrderItemDTO> orderItems;
    private AddressDTO address;
    // private PaymentDetailsDTO PaymentDetails;
    private Integer totalPrice;
    private Integer totalDiscountedPrice;
    private Integer discount;
    private String orderStatus;
    private int totalItems;
    private LocalDateTime deliveryDate;
    private LocalDateTime orderDate;
    private UserDTO user;
}

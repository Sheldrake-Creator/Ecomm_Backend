package com.dto;



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

    private Long id;
    private String orderId;
    private List<OrderItemDTO> orderItemsDto;
    private AddressDTO addressDto;
    private PaymentDetailsDTO PaymentDetailsDTO;
    private Integer totalPrice;
    private Integer totalDiscountedPrice;
    private Integer discount;
    private String orderStatus;
    private int totalItems;
    
    // private UserDTO userDTO;
}

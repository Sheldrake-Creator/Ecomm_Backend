package com.request;

import com.dto.AddressDTO;
import com.dto.CartDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private AddressDTO address;
    private CartDTO cart;

}

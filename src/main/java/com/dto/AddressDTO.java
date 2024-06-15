package com.dto;

import com.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    private Long addressId;
    private String firstName; 
    private String lastName;
    private String streetAdress;
    private UserDTO userDto;
    private String city;
    private String state;
    private String zipCode;
    private String mobile;
}

package com.mapper;

import org.mapstruct.Mapper;

import com.dto.AddressDTO;
import com.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDTO toAddressDTO(Address address);

    Address toAddress(AddressDTO addressDTO);
    
}

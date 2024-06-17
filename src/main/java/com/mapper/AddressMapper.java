package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.AddressDTO;
import com.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDTO toAddressDTO(Address address);
    
    @Mapping(target = "user", ignore = true)
    Address toAddress(AddressDTO addressDTO);
    
}

package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.AddressDTO;
import com.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "addressId", source = "address.addressId")
    @Mapping(target = "userId", source = "user.userId")
    AddressDTO toAddressDTO(Address address);

    // @Mapping(target = "addressId", source = "address.addressId")
    @Mapping(target = "user.userId", source = "userId")
    Address toAddress(AddressDTO addressDTO);

}

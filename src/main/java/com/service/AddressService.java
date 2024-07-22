package com.service;

import com.dto.AddressDTO;
import com.exception.AddressServiceException;

public interface AddressService {

    public AddressDTO addAddress(Long UserId, AddressDTO address) throws AddressServiceException;

    public AddressDTO updateAddress(Long UserId, AddressDTO address) throws AddressServiceException;

    public AddressDTO removeAddress(Long UserId, AddressDTO address) throws AddressServiceException;
}

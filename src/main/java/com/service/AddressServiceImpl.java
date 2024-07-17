package com.service;

import org.springframework.stereotype.Service;

import com.dto.AddressDTO;
import com.dto.UserDTO;
import com.exception.AddressServiceException;

import com.exception.UserServiceException;
import com.mapper.AddressMapper;
import com.mapper.UserMapper;
import com.model.Address;
import com.repository.AddressRepository;
import com.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;
    private final UserMapper userMapper;
    private final AddressRepository addressRepository;

    @Override
    public void addAddress(Long userId, AddressDTO shippingAddress) throws AddressServiceException {
        try {
            shippingAddress.setUserId(userId);
            Address updatedAddress = this.addressRepository.save(addressMapper.toAddress(shippingAddress));

            UserDTO user = this.userService.findUserById(userId);
            // Address address = this.addressRepository.findAddressByUserId(userId)
            // .orElseThrow(() -> new RepositoryException("Address Not Found"));
            user.getAddresses().add(addressMapper.toAddressDTO(updatedAddress));
            this.userRepository.save(userMapper.toUser(user));

        } catch (UserServiceException e) {
            throw new AddressServiceException("Error occurred while saving User Address: ", e);
        }
    }

    @Override
    public AddressDTO updateAddress(Long userId, AddressDTO address) throws AddressServiceException {
        address.setUserId(userId);
        Address updatedAdress = this.addressRepository.save(addressMapper.toAddress(address));

        return addressMapper.toAddressDTO(updatedAdress);
    }

    @Override
    public void removeAddress(Long userId, AddressDTO address) throws AddressServiceException {
        try {
            address.setUserId(userId);
            this.addressRepository.deleteAddressById(address.getAddressId());
            UserDTO user = this.userService.findUserById(userId);

            user.getAddresses().removeIf(addressId -> addressId.equals(address.getAddressId()));

            this.userRepository.save(userMapper.toUser(user));

        } catch (UserServiceException e) {
            throw new UnsupportedOperationException("Unimplemented method 'updateAddress'");
        }
    }

}

package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dto.AddressDTO;
import com.dto.OrderDTO;
import com.dto.UserDTO;
import com.exception.CartServiceException;
import com.exception.OrderServiceException;

@Service
public interface OrderService {

    public OrderDTO createOrder(UserDTO user, AddressDTO shippingAddress)
            throws CartServiceException, OrderServiceException;

    public OrderDTO findOrderById(Long orderId) throws OrderServiceException;

    public List<OrderDTO> usersOrderHistory(Long userId) throws OrderServiceException;

    public OrderDTO placedOrder(Long orderId) throws OrderServiceException;

    public OrderDTO confirmedOrder(Long orderId) throws OrderServiceException;

    public OrderDTO shippedOrder(Long orderId) throws OrderServiceException;

    public OrderDTO deliveredOrder(Long orderId) throws OrderServiceException;

    public OrderDTO canceledOrder(Long orderId) throws OrderServiceException;

    public List<OrderDTO> getAllOrders() throws OrderServiceException;

    public void deleteOrder(Long orderId) throws OrderServiceException;
}

package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dto.AddressDTO;
import com.dto.OrderDTO;
import com.dto.UserDTO;
import com.exception.CartException;
import com.exception.OrderException;

@Service
public interface OrderService {

    public OrderDTO createOrder(UserDTO user) throws CartException, OrderException;

    public OrderDTO findOrderById(Long orderId) throws OrderException;

    public List<OrderDTO> usersOrderHistory(Long userId) throws OrderException;

    public OrderDTO placedOrder(Long orderId) throws OrderException;

    public OrderDTO confirmedOrder(Long orderId) throws OrderException;

    public OrderDTO shippedOrder(Long orderId) throws OrderException;

    public OrderDTO deliveredOrder(Long orderId) throws OrderException;

    public OrderDTO canceledOrder(Long orderId) throws OrderException;

    public List<OrderDTO> getAllOrders() throws OrderException;

    public void deleteOrder(Long orderId) throws OrderException;

    public void addAddress(Long UserId, AddressDTO address) throws OrderException;
}

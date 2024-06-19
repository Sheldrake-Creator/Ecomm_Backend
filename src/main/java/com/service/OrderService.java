package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dto.AddressDTO;
import com.dto.OrderDTO;
import com.dto.UserDTO;
import com.exception.OrderException;

@Service
public interface OrderService {

    public OrderDTO createOrder(UserDTO user, AddressDTO shippingAddress);

    public OrderDTO findOrderById(Long orderId) throws OrderException;

    public List<OrderDTO> usersOrderHistory(Long userId);

    public OrderDTO placedOrder(Long orderId) throws OrderException;

    public OrderDTO confirmedOrder(Long orderId) throws OrderException;

    public OrderDTO shippedOrder(Long orderId) throws OrderException;

    public OrderDTO deliveredOrder(Long orderId) throws OrderException;

    public OrderDTO canceledOrder(Long orderId) throws OrderException;

    public List<OrderDTO> getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;
}

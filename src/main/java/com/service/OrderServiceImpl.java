package com.service;

import com.dto.*;
import com.mapper.*;
import com.model.*;
import com.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.exception.OrderException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressMapper addressMapper;
    private final UserMapper userMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO createOrder(UserDTO userDto, AddressDTO shippingAddress) {

        shippingAddress.setUser(userDto);
        userDto.getAddress().add(shippingAddress);

        Address address = addressMapper.toAddress(shippingAddress);
        User user = userMapper.toUser(userDto);
        addressRepository.save(address);
        userRepository.save(user);

        CartDTO cart = cartService.findUserCart(user.getUserId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItemDTO item : cart.getCartItems()) {
            OrderItemDTO orderItem = new OrderItemDTO();

            orderItem.setOrderPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUser(userDto);
            orderItem.setDiscountedPrice(item.getDiscountedPrice());

            OrderItem createdOrderItem = orderItemRepository.save(orderItemMapper.toOrderItem(orderItem));
            orderItems.add(createdOrderItem);
        }

        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setTotalItems(cart.getTotalItems());
        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.getPaymentDetails().setStatus("PENDING");
        createdOrder.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(createdOrder);

        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        return orderMapper.toOrderDTO(savedOrder);
    }

    @Override
    public OrderDTO placedOrder(Long orderId) throws OrderException {
        Order order = findOrderByIdEntity(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setStatus("COMPLETED");
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderByIdEntity(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderByIdEntity(orderId);
        order.setOrderStatus("SHIPPED");
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderByIdEntity(orderId);
        order.setOrderStatus("DELIVERED");
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO canceledOrder(Long orderId) throws OrderException {
        Order order = findOrderByIdEntity(orderId);
        order.setOrderStatus("CANCELLED");
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO findOrderById(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepository.findById(orderId);
        if (opt.isPresent()) {
            return orderMapper.toOrderDTO(opt.get());
        }
        throw new OrderException("Order does not exist with id " + orderId);
    }

    private Order findOrderByIdEntity(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepository.findById(orderId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new OrderException("Order does not exist with id " + orderId);
    }

    @Override
    public List<OrderDTO> usersOrderHistory(Long userId) {
        List<Order> orderEntity = orderRepository.getUsersOrders(userId);
        List<OrderDTO> orderDtos = new ArrayList<>();
        for (Order order : orderEntity) {
            orderDtos.add(orderMapper.toOrderDTO(order));
        }
        return orderDtos;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            orderDtos.add(orderMapper.toOrderDTO(order));
        }
        return orderDtos;
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        orderRepository.deleteById(orderId);
    }
}

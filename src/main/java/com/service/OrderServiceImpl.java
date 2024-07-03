package com.service;

import com.dto.*;
import com.mapper.*;
import com.model.*;
import com.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.exception.CartException;
import com.exception.OrderException;
import com.exception.RepositoryException;
import com.exception.UserException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartService cartService;
    private final UserService userService;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressMapper addressMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);

    public void addAddress(Long userId, AddressDTO shippingAddress) throws OrderException {
        try {
            shippingAddress.setUserId(userId);
            this.addressRepository.save(addressMapper.toAddress(shippingAddress));

            UserDTO user = this.userService.findUserById(userId);
            Address address = this.addressRepository.findAddressByUserId(userId)
                    .orElseThrow(() -> new RepositoryException("Address Not Found"));
            user.setAddressId(address.getAddressId());

        } catch (UserException e) {
            throw new OrderException("Error occurred while saving User Address: ", e);
        }

    }

    @Override
    public OrderDTO createOrder(UserDTO userDto) throws CartException, OrderException {

        Long addressId = userDto.getAddressId();
        Address addressEntity = this.addressRepository.findById(addressId)
                .orElseThrow(() -> new RepositoryException("Address Not Found"));
        AddressDTO address = addressMapper.toAddressDTO(addressEntity);

        CartDTO cart = cartService.findUserCart(userDto.getUserId());
        List<OrderItemDTO> orderItemList = new ArrayList<>();

        // Create initial OrderDTO without order items
        OrderDTO createdOrder = OrderDTO.builder().userId(userDto.getUserId()).orderItems(orderItemList)
                .totalPrice(cart.getTotalPrice()).totalDiscountedPrice(cart.getTotalDiscountedPrice())
                .totalItems(cart.getTotalItems()).shippingAddress(address).orderDate(LocalDateTime.now())
                .orderStatus("PENDING").deliveryDate(LocalDateTime.now().plusDays(5)).build();

        // Save the initial order to get the generated order ID
        Order savedOrder = orderRepository.save(orderMapper.toOrder(createdOrder));

        for (CartItemDTO item : cart.getCartItems()) {
            OrderItemDTO orderItem = OrderItemDTO.builder().orderPrice(item.getPrice())
                    .productId(item.getProduct().getProductId()).quantity(item.getQuantity()).size(item.getSize())
                    .discountedPrice(item.getDiscountedPrice()).orderId(savedOrder.getOrderId()).build();

            this.orderItemRepository.save(orderItemMapper.toOrderItem(orderItem));
            orderItemList.add(orderItem);
        }

        createdOrder.setOrderItems(orderItemList);
        // savedOrder = orderRepository.save(orderMapper.toOrder(createdOrder));

        this.placedOrder(savedOrder.getOrderId());
        this.cartService.checkoutCart(cart.getCartId());
        this.cartService.createCart(userDto.getUserId());

        return orderMapper.toOrderDTO(savedOrder);

        // for (OrderItemDTO item : orderItemList) {
        // item.setOrderId(savedOrder.getOrderId());
        // orderItemRepository.save(orderItemMapper.toOrderItem(item));
        // }
        // this.placedOrder(savedOrder.getOrderId());
        // this.cartService.checkoutCart(cart.getCartId());
        // this.cartService.createCart(userDto.getUserId());

    }

    @Override
    public OrderDTO placedOrder(Long orderId) throws OrderException {
        Order order = findOrderByIdEntity(orderId);
        order.setOrderStatus("PLACED");
        // order.getPaymentDetails().setStatus("COMPLETED");
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
    public List<OrderDTO> usersOrderHistory(Long userId) throws OrderException {
        logger.debug("UserID in UserOrderHistory() : {}", userId);
        Optional<List<Order>> optionalOrder = orderRepository.getUsersOrders(userId);
        List<OrderDTO> orderDtos = new ArrayList<>();
        if (!optionalOrder.isPresent()) {
            throw new OrderException("Order not found for UserID " + userId);
        }
        List<Order> orderEntity = optionalOrder.get();
        for (Order order : orderEntity) {
            logger.debug("Orders: {}", order);
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

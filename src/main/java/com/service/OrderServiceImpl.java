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
import com.exception.CartServiceException;
import com.exception.OrderServiceException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public OrderDTO createOrder(UserDTO user, AddressDTO shippingAddress, CartDTO cart)
            throws CartServiceException, OrderServiceException {

        // List<AddressDTO> addressId = userDto.getAddresses();
        // Address addressEntity = this.addressRepository.findById(addressId.get())
        // .orElseThrow(() -> new RepositoryException("Address Not Found"));
        // AddressDTO address = addressMapper.toAddressDTO(addressEntity);

        List<OrderItem> orderItemList = new ArrayList<>();
        logger.debug("userId: {}", user.getUserId());

        // Create initial OrderDTO without order items
        OrderDTO createdOrder = OrderDTO.builder().userId(user.getUserId()).totalPrice(cart.getTotalPrice())
                .totalDiscountedPrice(cart.getTotalDiscountedPrice()).totalItems(cart.getTotalItems())
                .shippingAddress(shippingAddress).orderDate(LocalDateTime.now()).orderStatus("PENDING")
                .deliveryDate(LocalDateTime.now().plusDays(5)).build();

        // Save the initial order to get the generated order ID
        Order savedOrder = orderRepository.save(orderMapper.toOrder(createdOrder));
        logger.debug("Saved Order: {}", savedOrder);

        for (CartItemDTO item : cart.getCartItems()) {
            OrderItemDTO orderItem = OrderItemDTO.builder().orderPrice(item.getPrice()).product(item.getProduct())
                    .quantity(item.getQuantity()).size(item.getSize()).discountedPrice(item.getDiscountedPrice())
                    .orderId(savedOrder.getOrderId()).build();

            OrderItem orderEntity = this.orderItemRepository.save(orderItemMapper.toOrderItem(orderItem));
            orderItemList.add(orderEntity);
        }

        savedOrder.setOrderItems(orderItemList);

        logger.debug("Saved Order: {}", savedOrder);
        this.orderRepository.save(savedOrder);

        // this.placedOrder(savedOrder.getOrderId()); /// This will need to be changed
        // to Pending
        this.cartService.checkoutCart(cart.getCartId());
        this.cartService.createCart(cart.getUserId());

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
    public OrderDTO placedOrder(Long orderId) throws OrderServiceException {
        Order order = findOrderByIdEntity(orderId);
        order.setOrderStatus("PLACED");
        // order.getPaymentDetails().setStatus("COMPLETED");
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO confirmedOrder(Long orderId) throws OrderServiceException {
        Order order = findOrderByIdEntity(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO shippedOrder(Long orderId) throws OrderServiceException {
        Order order = findOrderByIdEntity(orderId);
        order.setOrderStatus("SHIPPED");
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO deliveredOrder(Long orderId) throws OrderServiceException {
        Order order = findOrderByIdEntity(orderId);
        order.setOrderStatus("DELIVERED");
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO canceledOrder(Long orderId) throws OrderServiceException {
        Order order = findOrderByIdEntity(orderId);
        order.setOrderStatus("CANCELLED");
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO findOrderById(Long orderId) throws OrderServiceException {
        Optional<Order> opt = orderRepository.findById(orderId);
        if (opt.isPresent()) {
            return orderMapper.toOrderDTO(opt.get());
        }
        throw new OrderServiceException("Order does not exist with id " + orderId);
    }

    private Order findOrderByIdEntity(Long orderId) throws OrderServiceException {
        Optional<Order> opt = orderRepository.findById(orderId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new OrderServiceException("Order does not exist with id " + orderId);
    }

    @Override
    public List<OrderDTO> usersOrderHistory(Long userId) throws OrderServiceException {
        logger.debug("UserID in UserOrderHistory() : {}", userId);
        Optional<List<Order>> optionalOrder = orderRepository.getUsersOrders(userId);
        List<OrderDTO> orderDtos = new ArrayList<>();
        if (!optionalOrder.isPresent()) {
            throw new OrderServiceException("Order not found for UserID " + userId);
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
    public void deleteOrder(Long orderId) throws OrderServiceException {
        orderRepository.deleteById(orderId);
    }
}

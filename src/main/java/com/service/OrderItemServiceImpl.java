package com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.OrderItem;
import com.repository.OrderItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {

        return orderItemRepository.save(orderItem);
    }

}

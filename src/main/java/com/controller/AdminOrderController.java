package com.controller;

import com.dto.OrderDTO;
import com.exception.OrderException;
import com.response.APIResponse;
import com.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private OrderService orderService;

    // @GetMapping("/")
    public ResponseEntity<List<OrderDTO>> getAllOrdersHandler() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return new ResponseEntity<List<OrderDTO>>(orders, HttpStatus.ACCEPTED);
    }

    // @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<OrderDTO> ConfirmedOrderHandler(@PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws OrderException {
        OrderDTO order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // @PutMapping("/{orderId}/shipping")
    public ResponseEntity<OrderDTO> ShippedOrderHandler(@PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws OrderException {
        OrderDTO order = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // @PutMapping("/{orderId}/deliver")
    public ResponseEntity<OrderDTO> DeliverOrderHandler(@PathVariable Long orderId,
            @RequestHeader(" Authorization") String jwt) throws OrderException {
            OrderDTO order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderDTO> CancelOrderHandler(@PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws OrderException {
            OrderDTO order = orderService.canceledOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<APIResponse> DeleteOrderHandler(@PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws OrderException {
        orderService.deleteOrder(orderId);
        APIResponse res = new APIResponse();
        res.setMessage("order deleted successfully");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}

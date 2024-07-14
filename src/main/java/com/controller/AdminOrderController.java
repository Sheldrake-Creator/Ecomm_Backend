package com.controller;

import com.dto.OrderDTO;
import com.exception.OrderServiceException;
import com.response.HttpResponse;
import com.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

        private final OrderService orderService;
        private final Logger logger = LoggerFactory.getLogger(AdminOrderController.class);

        @GetMapping("/")
        public ResponseEntity<HttpResponse> getAllOrdersHandler() {
                try {
                        List<OrderDTO> orders = orderService.getAllOrders();
                        return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                        .data(Map.of("orders", orders)).message("Orders retrieved")
                                        .status(HttpStatus.ACCEPTED).statusCode(HttpStatus.ACCEPTED.value()).build());
                } catch (Exception e) {
                        logger.error("Error retrieving orders", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Error retrieving orders")
                                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
                }
        }

        @GetMapping("/{orderId}/confirmed")
        public ResponseEntity<HttpResponse> confirmedOrderHandler(@PathVariable Long orderId,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        OrderDTO order = orderService.confirmedOrder(orderId);
                        return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                        .data(Map.of("order", order)).message("Order confirmed").status(HttpStatus.OK)
                                        .statusCode(HttpStatus.OK.value()).build());
                } catch (OrderServiceException e) {
                        logger.error("Error confirming order", e);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Error confirming order")
                                                        .status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                } catch (Exception e) {
                        logger.error("Unexpected error confirming order", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Unexpected error confirming order")
                                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
                }
        }

        @GetMapping("/{orderId}/shipping")
        public ResponseEntity<HttpResponse> shippedOrderHandler(@PathVariable Long orderId,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        OrderDTO order = orderService.shippedOrder(orderId);
                        return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                        .data(Map.of("order", order)).message("Order shipped").status(HttpStatus.OK)
                                        .statusCode(HttpStatus.OK.value()).build());
                } catch (OrderServiceException e) {
                        logger.error("Error shipping order", e);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Error shipping order").status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                } catch (Exception e) {
                        logger.error("Unexpected error shipping order", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Unexpected error shipping order")
                                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
                }
        }

        @GetMapping("/{orderId}/deliver")
        public ResponseEntity<HttpResponse> deliveredOrderHandler(@PathVariable Long orderId,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        OrderDTO order = orderService.deliveredOrder(orderId);
                        return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                        .data(Map.of("order", order)).message("Order delivered").status(HttpStatus.OK)
                                        .statusCode(HttpStatus.OK.value()).build());
                } catch (OrderServiceException e) {
                        logger.error("Error delivering order", e);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Error delivering order")
                                                        .status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                } catch (Exception e) {
                        logger.error("Unexpected error delivering order", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Unexpected error delivering order")
                                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
                }
        }

        @PutMapping("/{orderId}/cancel")
        public ResponseEntity<HttpResponse> canceledOrdersHandler(@PathVariable Long orderId,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        OrderDTO order = orderService.canceledOrder(orderId);
                        return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                        .data(Map.of("order", order)).message("Order canceled").status(HttpStatus.OK)
                                        .statusCode(HttpStatus.OK.value()).build());
                } catch (OrderServiceException e) {
                        logger.error("Error canceling order", e);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Error canceling order").status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                } catch (Exception e) {
                        logger.error("Unexpected error canceling order", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Unexpected error canceling order")
                                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
                }
        }

        @DeleteMapping("/{orderId}/delete")
        public ResponseEntity<HttpResponse> deleteOrderHandler(@PathVariable Long orderId,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        orderService.deleteOrder(orderId);
                        return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                        .message("Order deleted successfully").status(HttpStatus.OK)
                                        .statusCode(HttpStatus.OK.value()).build());
                } catch (OrderServiceException e) {
                        logger.error("Error deleting order", e);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Error deleting order").status(HttpStatus.BAD_REQUEST)
                                                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
                } catch (Exception e) {
                        logger.error("Unexpected error deleting order", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                                                        .message("Unexpected error deleting order")
                                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
                }
        }
}

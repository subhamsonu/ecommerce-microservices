package com.smartmart.orderservice.controller;

import java.util.List;
import java.util.Optional;

import com.smartmart.orderservice.dto.OrderEventDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartmart.orderservice.entity.Order;
import com.smartmart.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> addOrder(@Valid @RequestBody OrderEventDto orderDto) {
        try {
            log.info("Creating new order with productId: {}, quantity: {}", orderDto.getProductId(), orderDto.getQuantity());
           Order order =  orderService.createOrder(orderDto);
            log.info("Order created successfully with orderId: {}", order.getOrderId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("order created successfully with orderId: " + order.getOrderId());
        } catch (IllegalArgumentException e) {
            log.error("Validation error while creating order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error creating order: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating order: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Integer id) {
        try {
            log.debug("Fetching order with id: {}", id);
            Optional<Order> order = orderService.getOrder(id);
            if (order.isPresent()) {
                log.debug("Order found with id: {}", id);
                return ResponseEntity.ok(order.get());
            } else {
                log.warn("Order not found with id: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("order not found with id: " + id);
            }
        } catch (IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error retrieving order: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving order: " + e.getMessage());
        }
    }

    /**
     * Get all orders
     *
     * @return ResponseEntity with list of all orders
     */
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        try {
            log.debug("Fetching all orders");
            List<Order> orders = orderService.getAllOrders();
            if (orders.isEmpty()) {
                log.warn("No orders found");
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No orders found");
            }
            log.info("Retrieved {} orders", orders.size());
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("Error retrieving orders: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving orders: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer id) {
        try {
            log.info("Deleting order with id: {}", id);
            boolean deleted = orderService.deleteOrder(id);
            if (deleted) {
                log.info("Order deleted successfully with id: {}", id);
                return ResponseEntity.ok("order deleted successfully with id: " + id);
            } else {
                log.warn("Order not found with id: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("order not found with id: " + id);
            }
        } catch (IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation Error: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("Error deleting order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error deleting order: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting order: " + e.getMessage());
        }
    }

}


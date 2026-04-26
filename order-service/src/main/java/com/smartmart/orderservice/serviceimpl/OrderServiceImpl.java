package com.smartmart.orderservice.serviceimpl;

import com.smartmart.orderservice.dto.*;
import com.smartmart.orderservice.entity.Order;
import com.smartmart.orderservice.exception.ProductNotFoundException;
import com.smartmart.orderservice.feignconfig.ProductClient;
import com.smartmart.orderservice.kafka.OrderProducer;
import com.smartmart.orderservice.repository.OrderRepository;
import com.smartmart.orderservice.service.OrderService;
import com.smartmart.orderservice.validator.OrderValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderValidator orderValidator;

	@Autowired
	private OrderProducer producer;

	@Autowired
	private ProductClient productClient;

	@Override
	public Order createOrder(OrderEventDto orderDto) {
		try {
			orderValidator.validateOrder(orderDto);
			ProductResponseDto product = productClient.getProductById(orderDto.getProductId());

			if (product == null) {
				throw new ProductNotFoundException("Product not found");
			}

			Double amount = product.getPrice() * orderDto.getQuantity();

			Order order = Order.builder().productId(orderDto.getProductId())
					.quantity(orderDto.getQuantity())
					.amount(amount)
					.orderId(generateUniqueOrderId())
					.status(OrderStatus.CREATED)
					.createdAt(LocalDateTime.now())
					.build();
			Order orderEntity = orderRepository.save(order);
			log.info("Order created successfully with ID: {}, orderId: {}", orderEntity.getId(), orderEntity.getOrderId());

			// Create and send event to Kafka
			OrderEventDto orderEventDto = OrderEventDto.builder().orderId(orderEntity.getOrderId())
					.productId(orderEntity.getProductId()).quantity(orderEntity.getQuantity())
					.totalAmount(orderEntity.getAmount()).build();

			producer.sendOrder(orderEventDto);
			log.info("Order event sent to Kafka for orderId: {}", orderEntity.getOrderId());

			return orderEntity;

		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			log.error("Error saving order: {}", e.getMessage(), e);
			throw new RuntimeException("Failed to create order: " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Order> getOrder(Integer id) {

		// Retrieve from repository
		log.debug("Fetching order with ID: {}", id);
		return orderRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Order> getAllOrders() {
		log.debug("Fetching all orders");
		return orderRepository.findAll();
	}

	@Override
	public boolean deleteOrder(Integer id) {

		if (orderRepository.existsById(id)) {
			orderRepository.deleteById(id);
			log.info("Order deleted successfully with ID: {}", id);
			return true;
		}

		log.error("Order not found with id: {}", id);
		throw new RuntimeException("Order not found with id: " + id);
	}

	@Override
	public void updateOrder(PaymentDto paymentDto) {
		// Validate payment DTO
		if (paymentDto == null || paymentDto.getOrderId() == null || paymentDto.getOrderId().isEmpty()) {
			log.error("Invalid payment details: orderId is required");
			throw new IllegalArgumentException("Invalid payment details: orderId is required");
		}

		log.info("Updating order for payment: orderId={}", paymentDto.getOrderId());

		// Find order by orderId
		Optional<Order> optionalOrder = orderRepository.findByOrderId(paymentDto.getOrderId());

		if (optionalOrder.isPresent()) {
			Order order = optionalOrder.get();

			// Update order status based on payment status
			if (paymentDto.getStatus() != null) {
				try {
					PaymentStatus status = PaymentStatus.fromString(paymentDto.getStatus());
					
					if (status == PaymentStatus.SUCCESS) {
						order.setStatus(OrderStatus.CONFIRMED);
						orderRepository.save(order);
						log.info("Order status updated to CONFIRMED for orderId: {}", paymentDto.getOrderId());
					} else if (status == PaymentStatus.FAILED) {
						order.setStatus(OrderStatus.CANCELLED);
						orderRepository.save(order);
						log.warn("Order status updated to CANCELLED for orderId: {} - Payment failed", paymentDto.getOrderId());
					}
				} catch (IllegalArgumentException e) {
					log.error("Invalid payment status '{}' for orderId: {}", paymentDto.getStatus(), paymentDto.getOrderId());
					throw new IllegalArgumentException("Invalid payment status: " + paymentDto.getStatus() + ". Expected: SUCCESS or FAILED");
				}
			} else {
				log.error("Payment status is required for orderId: {}", paymentDto.getOrderId());
				throw new IllegalArgumentException("Payment status is required");
			}
		} else {
			log.error("Order not found with orderId: {}", paymentDto.getOrderId());
			throw new RuntimeException("Order not found with orderId: " + paymentDto.getOrderId());
		}
	}

	/**
	 * Generates a unique orderId using combination of timestamp and UUID
	 * Format: ORD-<timestamp>-<uuid-short>
	 * Example: ORD-1713015835-a3c5f2
	 */
	private String generateUniqueOrderId() {
		long timestamp = System.currentTimeMillis();
		String uuid = UUID.randomUUID().toString().substring(0, 8);
		String uniqueOrderId = "ORD-" + timestamp + "-" + uuid;
		log.debug("Generated unique orderId: {}", uniqueOrderId);
		return uniqueOrderId;
	}

}

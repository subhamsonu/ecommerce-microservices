package com.smartmart.orderservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.smartmart.orderservice.dto.PaymentDto;
import com.smartmart.orderservice.service.OrderService;

@Service
public class OrderConsumer {

	private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);
	private static final String TOPIC = "payment-topic";
	
	@Autowired
	private OrderService orderService;
	
	@KafkaListener(topics = TOPIC, groupId = "order-service-group", containerFactory = "paymentListenerContainerFactory")
	public void consumePayment(PaymentDto paymentDto) {
		try {
			log.info("Received payment event for orderId: {}, status: {}", paymentDto.getOrderId(), paymentDto.getStatus());
			orderService.updateOrder(paymentDto);
			log.info("Successfully processed payment for orderId: {}", paymentDto.getOrderId());
		} catch (Exception e) {
			log.error("Error processing payment for orderId: {}: {}", paymentDto.getOrderId(), e.getMessage(), e);
			throw e;
		}
	}
	
}


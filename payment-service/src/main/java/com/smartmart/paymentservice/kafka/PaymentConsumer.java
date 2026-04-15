package com.smartmart.paymentservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.smartmart.paymentservice.dto.OrderEventDto;
import com.smartmart.paymentservice.service.PaymentService;

@Service
public class PaymentConsumer {

	private static final Logger log = LoggerFactory.getLogger(PaymentConsumer.class);
	private static final String TOPIC = "order-topic";
	
	@Autowired
	private PaymentService paymentService;
	
	
	@KafkaListener(topics = TOPIC, groupId = "payment-service-group", containerFactory = "kafkaListenerContainerFactory")
	public void consumePayment(OrderEventDto orderEventDto) {
		try {
			log.info("Received order event for orderId: {}, productId: {}", orderEventDto.getOrderId(), orderEventDto.getProductId());
			paymentService.processPayment(orderEventDto);
			log.info("Successfully processed payment for orderId: {}", orderEventDto.getOrderId());
		} catch (Exception e) {
			log.error("Error processing payment for orderId: {}: {}", orderEventDto.getOrderId(), e.getMessage(), e);
			throw e;
		}
	}
}


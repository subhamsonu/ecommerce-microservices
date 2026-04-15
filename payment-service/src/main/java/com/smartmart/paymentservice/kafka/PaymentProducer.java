package com.smartmart.paymentservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.smartmart.paymentservice.dto.PaymentDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentProducer {
	
	private static final Logger log = LoggerFactory.getLogger(PaymentProducer.class);
	private static final String TOPIC = "payment-topic";
     
	@Autowired
	@Qualifier("paymentKafkaTemplate")
	private  KafkaTemplate<String, PaymentDto> kafkaTemplate;
	
	
	public void sendPayment(PaymentDto paymentDto) {
		try {
			log.info("Sending payment event to Kafka topic '{}': orderId={}, status={}", TOPIC, paymentDto.getOrderId(), paymentDto.getStatus());
			kafkaTemplate.send(TOPIC, paymentDto);
			log.debug("Payment event sent successfully for orderId: {}", paymentDto.getOrderId());
		} catch (Exception e) {
			log.error("Error sending payment event for orderId: {}: {}", paymentDto.getOrderId(), e.getMessage(), e);
			throw new RuntimeException("Failed to send payment event: " + e.getMessage(), e);
		}
	}
}


package com.smartmart.paymentservice.serviceimpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartmart.paymentservice.dto.OrderEventDto;
import com.smartmart.paymentservice.dto.PaymentDto;
import com.smartmart.paymentservice.dto.PaymentStatus;
import com.smartmart.paymentservice.entity.Payment;
import com.smartmart.paymentservice.kafka.PaymentProducer;
import com.smartmart.paymentservice.repository.PaymentRepository;
import com.smartmart.paymentservice.service.PaymentService;
import com.smartmart.paymentservice.validator.PaymentValidator;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

	private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PaymentValidator paymentValidator;

	@Autowired
	private PaymentProducer paymentProducer;

	@Override
	public void processPayment(OrderEventDto orderEventDto) {
		try {
			log.info("Processing payment for orderId: {}, amount: {}", orderEventDto.getOrderId(), orderEventDto.getTotalAmount());
			
			Payment payment = Payment.builder()
					.orderId(orderEventDto.getOrderId())
					.amount(orderEventDto.getTotalAmount())
					.status(PaymentStatus.PENDING)
					.createdAt(LocalDateTime.now())
					.build();

			// TODO: Replace with actual payment gateway integration
			// For now, simulate payment success/failure (65% success rate)
			boolean success = Math.random() > 0.35;

			if (success) {
				payment.setStatus(PaymentStatus.SUCCESS);
				payment.setFailureReason(null); // No failure reason for successful payments
				log.info("Payment processing successful for orderId: {}", orderEventDto.getOrderId());
			} else {
				payment.setStatus(PaymentStatus.FAILED);
				payment.setFailureReason("Payment processing failed due to insufficient funds or network issues.");
				log.warn("Payment processing failed for orderId: {}: {}", orderEventDto.getOrderId(), payment.getFailureReason());
			}

			paymentRepository.save(payment);
			log.info("Payment record saved for orderId: {} with status: {}", orderEventDto.getOrderId(), payment.getStatus());

			PaymentDto paymentDto = PaymentDto.builder()
					.orderId(payment.getOrderId())
					.productId(orderEventDto.getProductId())
					.quantity(orderEventDto.getQuantity())
					.amount(payment.getAmount())
					.status(payment.getStatus().toString())
					.failureReason(payment.getFailureReason())
					.createdAt(payment.getCreatedAt())
					.build();

			paymentProducer.sendPayment(paymentDto);
			log.info("Payment event published to Kafka for orderId: {}", orderEventDto.getOrderId());

		} catch (Exception e) {
			log.error("Error processing payment for orderId: {}: {}", orderEventDto.getOrderId(), e.getMessage(), e);
			throw new RuntimeException("Failed to process payment: " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Payment> getPaymentById(Integer id) {
		log.debug("Fetching payment with id: {}", id);
		paymentValidator.validatePaymentId(id);
		return paymentRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Payment> getPaymentByOrderId(String orderId) {
		log.debug("Fetching payment for orderId: {}", orderId);
		paymentValidator.validateOrderId(orderId);
		return paymentRepository.findByOrderId(orderId);
	}

}


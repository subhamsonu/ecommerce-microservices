package com.smartmart.paymentservice.service;

import java.util.Optional;

import com.smartmart.paymentservice.dto.OrderEventDto;
import com.smartmart.paymentservice.entity.Payment;

public interface PaymentService {

	void processPayment(OrderEventDto orderEventDto);

	Optional<Payment> getPaymentById(Integer id);

	Optional<Payment> getPaymentByOrderId(String orderId);

}

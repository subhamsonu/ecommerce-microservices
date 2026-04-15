package com.smartmart.orderservice.service;

import java.util.List;
import java.util.Optional;

import com.smartmart.orderservice.dto.OrderEventDto;
import com.smartmart.orderservice.dto.PaymentDto;
import com.smartmart.orderservice.entity.Order;

public interface OrderService {
	
	Order createOrder(OrderEventDto Order);
	
	Optional<Order> getOrder(Integer id);
	
	List<Order> getAllOrders();
	
	boolean deleteOrder(Integer id);
	
	void updateOrder(PaymentDto paymentDto);

}

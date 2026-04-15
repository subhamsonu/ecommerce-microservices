package com.smartmart.orderservice.service;

import com.smartmart.orderservice.dto.OrderEventDto;

public interface OrderProducerService {
	
	public void sendOrder(OrderEventDto orderEbent);

}

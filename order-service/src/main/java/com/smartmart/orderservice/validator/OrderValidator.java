package com.smartmart.orderservice.validator;

import com.smartmart.orderservice.dto.OrderEventDto;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

	public void validateOrder(OrderEventDto orderDto) {

		if (orderDto == null) {
			throw new IllegalArgumentException("orderDto cannot be null");
		}

		if (orderDto.getProductId() == null || orderDto.getProductId() <= 0) {
			throw new IllegalArgumentException("ProductId cannot be null or empty");
		}

		if (orderDto.getQuantity() == null || orderDto.getQuantity() <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than 0");
		}
	}

}

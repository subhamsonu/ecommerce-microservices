package com.smartmart.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDto {
	private String orderId;
    private Integer productId;
    private Integer quantity;
    private Double totalAmount;
}

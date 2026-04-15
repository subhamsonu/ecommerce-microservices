package com.smartmart.orderservice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private String orderId;
    private String productId;
    private Integer quantity;
    private Double amount;
    private String status;        // PAYMENT_COMPLETED, PAYMENT_FAILED
    private String failureReason; // null if success
    private LocalDateTime createdAt;
}

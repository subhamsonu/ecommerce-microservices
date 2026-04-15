package com.smartmart.inventory_service.dto;

public enum PaymentStatus {
	SUCCESS("SUCCESS"),
	FAILED("FAILED");
	
	private final String value;
	
	PaymentStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static PaymentStatus fromString(String value) {
		for (PaymentStatus status : PaymentStatus.values()) {
			if (status.value.equalsIgnoreCase(value)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid payment status: " + value);
	}
}

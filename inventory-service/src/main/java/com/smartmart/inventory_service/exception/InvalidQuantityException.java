package com.smartmart.inventory_service.exception;

public class InvalidQuantityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidQuantityException(String message) {
		super(message);
	}

	public InvalidQuantityException(String message, Throwable cause) {
		super(message, cause);
	}
}

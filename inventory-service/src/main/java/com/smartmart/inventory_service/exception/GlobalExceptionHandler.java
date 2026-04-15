package com.smartmart.inventory_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(InventoryNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleInventoryNotFound(InventoryNotFoundException ex) {
		log.error("Inventory not found: {}", ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(
			ex.getMessage(),
			HttpStatus.NOT_FOUND.value(),
			"INVENTORY_NOT_FOUND"
		);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(InsufficientInventoryException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientInventory(InsufficientInventoryException ex) {
		log.error("Insufficient inventory: {}", ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(
			ex.getMessage(),
			HttpStatus.BAD_REQUEST.value(),
			"INSUFFICIENT_INVENTORY"
		);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(InvalidQuantityException.class)
	public ResponseEntity<ErrorResponse> handleInvalidQuantity(InvalidQuantityException ex) {
		log.error("Invalid quantity: {}", ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(
			ex.getMessage(),
			HttpStatus.BAD_REQUEST.value(),
			"INVALID_QUANTITY"
		);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		log.error("Validation error: {}", ex.getBindingResult().getFieldError());
		
		Map<String, String> fieldErrors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->
			fieldErrors.put(error.getField(), error.getDefaultMessage())
		);
		
		ErrorResponse errorResponse = new ErrorResponse(
			"Validation failed",
			HttpStatus.BAD_REQUEST.value(),
			"VALIDATION_ERROR",
			fieldErrors
		);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		log.error("Unexpected error: {}", ex.getMessage(), ex);
		ErrorResponse errorResponse = new ErrorResponse(
			"An unexpected error occurred",
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			"INTERNAL_SERVER_ERROR"
		);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	public static class ErrorResponse {
		private String message;
		private int status;
		private String errorCode;
		private LocalDateTime timestamp;
		private Map<String, String> fieldErrors;

		public ErrorResponse(String message, int status, String errorCode) {
			this.message = message;
			this.status = status;
			this.errorCode = errorCode;
			this.timestamp = LocalDateTime.now();
		}

		public ErrorResponse(String message, int status, String errorCode, Map<String, String> fieldErrors) {
			this.message = message;
			this.status = status;
			this.errorCode = errorCode;
			this.timestamp = LocalDateTime.now();
			this.fieldErrors = fieldErrors;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}

		public Map<String, String> getFieldErrors() {
			return fieldErrors;
		}

		public void setFieldErrors(Map<String, String> fieldErrors) {
			this.fieldErrors = fieldErrors;
		}
	}
}

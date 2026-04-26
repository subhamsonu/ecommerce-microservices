package com.smartmart.inventory_service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartmart.inventory_service.entity.Inventory;
import com.smartmart.inventory_service.exception.InvalidQuantityException;
import com.smartmart.inventory_service.exception.InventoryNotFoundException;
import com.smartmart.inventory_service.exception.InsufficientInventoryException;

public class InventoryValidator {

	private static final Logger log = LoggerFactory.getLogger(InventoryValidator.class);

	public static void validateQuantity(Integer quantity) {
		if (quantity == null || quantity <= 0) {
			log.error("Invalid quantity: {}", quantity);
			throw new InvalidQuantityException("Quantity must be positive and greater than zero. Provided: " + quantity);
		}
	}

	public static void validateProductId(Integer productId) {
		if (productId == null || productId <= 0) {
			log.error("Invalid product ID: {}", productId);
			throw new InvalidQuantityException("Product ID must be positive and greater than zero. Provided: " + productId);
		}
	}

	public static void validateInventoryId(Integer id) {
		if (id == null || id <= 0) {
			log.error("Invalid inventory ID: {}", id);
			throw new InvalidQuantityException("Inventory ID must be positive and greater than zero. Provided: " + id);
		}
	}

	public static void validateInventoryExists(Inventory inventory, Integer productId) {
		if (inventory == null) {
			log.error("Inventory not found for product ID: {}", productId);
			throw new InventoryNotFoundException(
					"Inventory not found for product ID: " + productId);
		}
	}


	public static void validateInventoryExistsById(Inventory inventory, Integer id) {
		if (inventory == null) {
			log.error("Inventory not found with ID: {}", id);
			throw new InventoryNotFoundException("Inventory not found with ID: " + id);
		}
	}

	public static void validateSufficientQuantity(Integer currentQuantity, Integer quantityToReduce) {
		if (currentQuantity < quantityToReduce) {
			log.error("Insufficient inventory. Current quantity: {}, Requested to reduce: {}", currentQuantity, quantityToReduce);
			throw new InsufficientInventoryException(
					"Insufficient inventory. Current quantity: " + currentQuantity + ", Requested to reduce: " + quantityToReduce);
		}
	}

	public static void validateInventoryObject(Inventory inventory) {
		if (inventory == null) {
			log.error("Inventory object cannot be null");
			throw new InvalidQuantityException("Inventory object cannot be null");
		}
		if (inventory.getProductId() == null || inventory.getProductId() <= 0) {
			log.error("Invalid product ID in inventory object: {}", inventory.getProductId());
			throw new InvalidQuantityException("Inventory must have a valid product ID");
		}
		if (inventory.getQuantity() == null || inventory.getQuantity() < 0) {
			log.error("Invalid quantity in inventory object: {}", inventory.getQuantity());
			throw new InvalidQuantityException("Inventory quantity cannot be negative");
		}
	}

	public static void validateNonNegativeQuantity(Integer quantity) {
		if (quantity == null || quantity < 0) {
			log.error("Invalid non-negative quantity: {}", quantity);
			throw new InvalidQuantityException("Quantity cannot be negative. Provided: " + quantity);
		}
	}

	public static boolean hassufficientQuantity(Inventory inventory, Integer quantityToReduce) {
		return inventory != null && inventory.getQuantity() >= quantityToReduce;
	}

}

package com.smartmart.inventory_service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartmart.inventory_service.entity.Inventory;
import com.smartmart.inventory_service.exception.InvalidQuantityException;
import com.smartmart.inventory_service.exception.InventoryNotFoundException;
import com.smartmart.inventory_service.exception.InsufficientInventoryException;

public class InventoryValidator {

	private static final Logger log = LoggerFactory.getLogger(InventoryValidator.class);

	/**
	 * Validates if the quantity is positive and non-zero
	 * 
	 * @param quantity - quantity to validate
	 * @throws InvalidQuantityException if quantity is invalid
	 */
	public static void validateQuantity(Integer quantity) {
		if (quantity == null || quantity <= 0) {
			log.error("Invalid quantity: {}", quantity);
			throw new InvalidQuantityException("Quantity must be positive and greater than zero. Provided: " + quantity);
		}
	}

	/**
	 * Validates if the product ID is valid (not null and positive)
	 * 
	 * @param productId - product ID to validate
	 * @throws InvalidQuantityException if product ID is invalid
	 */
	public static void validateProductId(Integer productId) {
		if (productId == null || productId <= 0) {
			log.error("Invalid product ID: {}", productId);
			throw new InvalidQuantityException("Product ID must be positive and greater than zero. Provided: " + productId);
		}
	}

	/**
	 * Validates if the inventory ID is valid (not null and positive)
	 * 
	 * @param id - inventory ID to validate
	 * @throws InvalidQuantityException if ID is invalid
	 */
	public static void validateInventoryId(Integer id) {
		if (id == null || id <= 0) {
			log.error("Invalid inventory ID: {}", id);
			throw new InvalidQuantityException("Inventory ID must be positive and greater than zero. Provided: " + id);
		}
	}

	/**
	 * Validates if the inventory object exists (not null)
	 * 
	 * @param inventory - inventory object to validate
	 * @param productId - product ID for error message
	 * @throws InventoryNotFoundException if inventory is null
	 */
	public static void validateInventoryExists(Inventory inventory, Integer productId) {
		if (inventory == null) {
			log.error("Inventory not found for product ID: {}", productId);
			throw new InventoryNotFoundException(
					"Inventory not found for product ID: " + productId);
		}
	}

	/**
	 * Validates if inventory exists by ID
	 * 
	 * @param inventory - inventory object to validate
	 * @param id - inventory ID for error message
	 * @throws InventoryNotFoundException if inventory is null
	 */
	public static void validateInventoryExistsById(Inventory inventory, Integer id) {
		if (inventory == null) {
			log.error("Inventory not found with ID: {}", id);
			throw new InventoryNotFoundException("Inventory not found with ID: " + id);
		}
	}

	/**
	 * Validates if the current inventory has sufficient quantity to reduce
	 * 
	 * @param currentQuantity - current quantity in inventory
	 * @param quantityToReduce - quantity to reduce
	 * @throws InsufficientInventoryException if quantity is insufficient
	 */
	public static void validateSufficientQuantity(Integer currentQuantity, Integer quantityToReduce) {
		if (currentQuantity < quantityToReduce) {
			log.error("Insufficient inventory. Current quantity: {}, Requested to reduce: {}", currentQuantity, quantityToReduce);
			throw new InsufficientInventoryException(
					"Insufficient inventory. Current quantity: " + currentQuantity + ", Requested to reduce: " + quantityToReduce);
		}
	}

	/**
	 * Validates the inventory object (not null and has valid data)
	 * 
	 * @param inventory - inventory object to validate
	 * @throws InvalidQuantityException if inventory is invalid
	 */
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

	/**
	 * Validates quantity is non-negative (for initialization)
	 * 
	 * @param quantity - quantity to validate
	 * @throws InvalidQuantityException if quantity is negative
	 */
	public static void validateNonNegativeQuantity(Integer quantity) {
		if (quantity == null || quantity < 0) {
			log.error("Invalid non-negative quantity: {}", quantity);
			throw new InvalidQuantityException("Quantity cannot be negative. Provided: " + quantity);
		}
	}

	/**
	 * Checks if quantity is available for reduction
	 * 
	 * @param inventory - inventory object
	 * @param quantityToReduce - quantity to reduce
	 * @return true if sufficient, false otherwise
	 */
	public static boolean hassufficientQuantity(Inventory inventory, Integer quantityToReduce) {
		return inventory != null && inventory.getQuantity() >= quantityToReduce;
	}

}

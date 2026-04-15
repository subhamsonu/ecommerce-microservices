package com.smartmart.inventory_service.serviceImpl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartmart.inventory_service.dto.InventoryStatus;
import com.smartmart.inventory_service.dto.PaymentDto;
import com.smartmart.inventory_service.entity.Inventory;
import com.smartmart.inventory_service.exception.InventoryNotFoundException;
import com.smartmart.inventory_service.repository.InventoryRepository;
import com.smartmart.inventory_service.service.InventoryService;
import com.smartmart.inventory_service.util.InventoryValidator;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

	private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

	@Autowired
	private InventoryRepository inventoryRepository;

	@Override
	@Transactional(readOnly = true)
	public Inventory findByProductId(Integer productId) {
		// Validate product ID
		InventoryValidator.validateProductId(productId);

		// Fetch inventory from repository
		Inventory inventory = inventoryRepository.findByProductId(productId);

		// Validate that inventory exists
		InventoryValidator.validateInventoryExists(inventory, productId);
		log.info("Found inventory for product ID: {}", productId);

		return inventory;
	}

	@Override
	public void deleteByProductId(Integer productId) {

		InventoryValidator.validateProductId(productId);

		Inventory inventory = inventoryRepository.findByProductId(productId);
		InventoryValidator.validateInventoryExists(inventory, productId);

		inventoryRepository.deleteByProductId(productId);
		log.info("Deleted inventory for product ID: {}", productId);
	}

	@Override
	public Inventory saveInventory(Inventory inventory) {

		InventoryValidator.validateInventoryObject(inventory);

		inventory.setReservedQuantity(0);
		Inventory savedInventory = inventoryRepository.save(inventory);
		log.info("Saved inventory for product ID: {}", inventory.getProductId());
		return savedInventory;
	}

	@Override
	public void deleteInventory(Integer id) {
		InventoryValidator.validateInventoryId(id);

		inventoryRepository.deleteById(id);
		log.info("Deleted inventory with ID: {}", id);
	}

	@Override
	public void reserveInventory(Integer productId, Integer quantity) {

		InventoryValidator.validateProductId(productId);
		InventoryValidator.validateQuantity(quantity);

		Inventory inventory = inventoryRepository.findByProductId(productId);
		if (inventory != null) {
			InventoryValidator.validateSufficientQuantity(inventory.getQuantity(), quantity);

			inventory.setQuantity(inventory.getQuantity() - quantity);
			inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);

			if (inventory.getQuantity() == 0) {
				inventory.setStatus(InventoryStatus.RESERVED);
			} else {
				inventory.setStatus(InventoryStatus.IN_STOCK);
			}
			inventory.setUpdatedAt(LocalDateTime.now());

			inventoryRepository.save(inventory);
			log.info("Reserved {} units for product ID: {}", quantity, productId);

		} else {
			log.error("Inventory not found for product ID: {}", productId);
			throw new InventoryNotFoundException("Inventory not found for product ID: " + productId);
		}
	}

	@Override
	public void releaseStock(PaymentDto paymentDto) {

		Inventory inventory = inventoryRepository.findByProductId(paymentDto.getProductId());
		if (inventory == null) {
			log.error("Inventory not found for product ID: {}", paymentDto.getProductId());
			throw new InventoryNotFoundException("Inventory not found for productId: " + paymentDto.getProductId());
		}

		// Validate that we have enough reserved quantity to release
		if (inventory.getReservedQuantity() < paymentDto.getQuantity()) {
			log.error("Cannot release {} units - only {} units reserved for product ID: {}",
				paymentDto.getQuantity(), inventory.getReservedQuantity(), paymentDto.getProductId());
			throw new IllegalStateException("Insufficient reserved quantity to release for product ID: " + paymentDto.getProductId());
		}

		inventory.setQuantity(inventory.getQuantity() + paymentDto.getQuantity());
		inventory.setReservedQuantity(inventory.getReservedQuantity() - paymentDto.getQuantity());
		inventory.setStatus(InventoryStatus.IN_STOCK);
		inventory.setUpdatedAt(LocalDateTime.now());
		inventoryRepository.save(inventory);
		log.info("Released {} units for product ID: {}", paymentDto.getQuantity(), paymentDto.getProductId());
	}

	@Override
	public void deductStock(PaymentDto paymentDto) {

		Inventory inventory = inventoryRepository.findByProductId(paymentDto.getProductId());
		if (inventory == null) {
			log.error("Inventory not found for product ID: {}", paymentDto.getProductId());
			throw new InventoryNotFoundException("Inventory not found for productId: " + paymentDto.getProductId());
		}

		// Validate that we have enough reserved quantity to deduct
		if (inventory.getReservedQuantity() < paymentDto.getQuantity()) {
			log.error("Cannot deduct {} units - only {} units reserved for product ID: {}",
				paymentDto.getQuantity(), inventory.getReservedQuantity(), paymentDto.getProductId());
			throw new IllegalStateException("Insufficient reserved quantity to deduct for product ID: " + paymentDto.getProductId());
		}

		inventory.setReservedQuantity(inventory.getReservedQuantity() - paymentDto.getQuantity());

		if (inventory.getQuantity() == 0 && inventory.getReservedQuantity() == 0) {
			inventory.setStatus(InventoryStatus.OUT_OF_STOCK);
		} else {
			inventory.setStatus(InventoryStatus.IN_STOCK);
		}

		inventory.setUpdatedAt(LocalDateTime.now());
		inventoryRepository.save(inventory);
		log.info("Deducted {} units from product ID: {}", paymentDto.getQuantity(), paymentDto.getProductId());

	}

}

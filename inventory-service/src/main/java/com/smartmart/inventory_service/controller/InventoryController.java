package com.smartmart.inventory_service.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartmart.inventory_service.entity.Inventory;
import com.smartmart.inventory_service.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

	private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

	@Autowired
	private InventoryService inventoryService;

	@GetMapping("/{productId}")
	public ResponseEntity<Inventory> findByProductId(@PathVariable Integer productId) {
		log.info("Fetching inventory for product ID: {}", productId);
		Inventory inventory = inventoryService.findByProductId(productId);
		if (inventory != null) {
			log.debug("Inventory found for product ID: {}", productId);
			return ResponseEntity.ok(inventory);
		}
		log.warn("Inventory not found for product ID: {}", productId);
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Inventory> saveInventory(@Valid @RequestBody Inventory inventory) {
		log.info("Saving inventory for product ID: {}", inventory.getProductId());
		Inventory savedInventory = inventoryService.saveInventory(inventory);
		log.info("Inventory saved successfully for product ID: {}", inventory.getProductId());
		return ResponseEntity.status(HttpStatus.CREATED).body(savedInventory);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteInventory(@PathVariable Integer id) {
		log.info("Deleting inventory with ID: {}", id);
		try {
			inventoryService.deleteInventory(id);
			log.info("Inventory deleted successfully with ID: {}", id);
			return ResponseEntity.ok("Inventory deleted successfully");
		} catch (Exception e) {
			log.error("Error deleting inventory with ID: {}", id, e);
			return ResponseEntity.badRequest().body("Error deleting inventory: " + e.getMessage());
		}
	}

	@DeleteMapping("/product/{productId}")
	public ResponseEntity<String> deleteByProductId(@PathVariable Integer productId) {
		log.info("Deleting inventory for product ID: {}", productId);
		try {
			inventoryService.deleteByProductId(productId);
			log.info("Inventory deleted successfully for product ID: {}", productId);
			return ResponseEntity.ok("Inventory deleted successfully");
		} catch (Exception e) {
			log.error("Error deleting inventory for product ID: {}", productId, e);
			return ResponseEntity.badRequest().body("Error deleting inventory: " + e.getMessage());
		}
	}

	// Helper class for request body
	@lombok.Data
	@lombok.NoArgsConstructor
	@lombok.AllArgsConstructor
	public static class QuantityRequest {
		private Integer quantity;
	}

}

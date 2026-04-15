package com.smartmart.inventory_service.entity;

import java.time.LocalDateTime;

import com.smartmart.inventory_service.dto.InventoryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "inventory")
public class Inventory {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "Product ID cannot be null")
	@Positive(message = "Product ID must be positive")
	@Column(name = "product_id", nullable = false)
	private Integer productId;
	
	@NotNull(message = "Quantity cannot be null")
	@PositiveOrZero(message = "Quantity must be non-negative")
	private Integer quantity;
	
	@NotNull(message = "Reserved quantity cannot be null")
	@PositiveOrZero(message = "Reserved quantity must be non-negative")
	@Column(name = "reserved_quantity", nullable = false)
	private Integer reservedQuantity;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private InventoryStatus status;
	
	@Column(name = "updated_at", nullable = false, updatable = false)
	private LocalDateTime updatedAt;

}

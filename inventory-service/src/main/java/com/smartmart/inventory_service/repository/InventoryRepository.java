package com.smartmart.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smartmart.inventory_service.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
	
	Inventory findByProductId(Integer productId);
	
	@Transactional
	void deleteByProductId(Integer productId);

}

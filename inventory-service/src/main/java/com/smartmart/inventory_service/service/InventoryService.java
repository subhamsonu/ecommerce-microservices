package com.smartmart.inventory_service.service;

import com.smartmart.inventory_service.dto.OrderEventDto;
import com.smartmart.inventory_service.dto.PaymentDto;
import com.smartmart.inventory_service.entity.Inventory;

public interface InventoryService {
	
	Inventory findByProductId(Integer productId);
	
	 void deleteByProductId(Integer productId);
	
	 Inventory saveInventory(Inventory inventory);
	
	void deleteInventory(Integer id);
	
	 void reserveInventory(OrderEventDto orderEventDto);
	 	 
	 public void releaseStock(PaymentDto paymentDto);
	 
	 public void deductStock(PaymentDto paymentDto);
	 

}

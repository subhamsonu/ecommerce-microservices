package com.smartmart.orderservice.feignconfig;

import com.smartmart.orderservice.dto.InventoryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/api/inventory/{productId}")
    InventoryResponseDto getInventoryByProductId(@PathVariable Integer productId);

}

package com.smartmart.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmart.productservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	
}

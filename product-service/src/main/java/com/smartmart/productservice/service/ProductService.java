package com.smartmart.productservice.service;

import java.util.List;

import com.smartmart.productservice.dto.ProductDto;
import com.smartmart.productservice.entity.Product;

public interface ProductService {

	public void addProduct(ProductDto productDto);
	public Product getProductById(Integer id);
	public List<Product> getAllProducts();
}

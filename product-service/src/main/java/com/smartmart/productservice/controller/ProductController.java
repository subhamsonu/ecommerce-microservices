package com.smartmart.productservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartmart.productservice.dto.ProductDto;
import com.smartmart.productservice.entity.Product;
import com.smartmart.productservice.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@PostMapping("/add")
	public ResponseEntity<String> addProduct(@RequestBody ProductDto product) {
		try {
			log.info("Adding new product: {}", product.getName());
			productService.addProduct(product);
			log.info("Product added successfully: {}", product.getName());
			return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			log.error("Validation error while adding product: {}", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Error adding product: {}", e.getMessage(), e);
			return new ResponseEntity<>("Error adding product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
		try {
			log.debug("Fetching product with id: {}", id);
			Product product = productService.getProductById(id);
			if (product != null) {
				log.debug("Product found with id: {}", id);
				return new ResponseEntity<>(product, HttpStatus.OK);
			}
			log.warn("Product not found with id: {}", id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Error retrieving product with id: {}: {}", id, e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProducts() {
		try {
			log.debug("Fetching all products");
			List<Product> products = productService.getAllProducts();
			if (!products.isEmpty()) {
				log.info("Retrieved {} products", products.size());
				return new ResponseEntity<>(products, HttpStatus.OK);
			}
			log.warn("No products found");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.error("Error retrieving all products: {}", e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

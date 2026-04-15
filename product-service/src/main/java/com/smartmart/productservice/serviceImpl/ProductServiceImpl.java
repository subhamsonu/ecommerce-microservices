package com.smartmart.productservice.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartmart.productservice.dto.ProductDto;
import com.smartmart.productservice.entity.Product;
import com.smartmart.productservice.repository.ProductRepository;
import com.smartmart.productservice.service.ProductService;
import com.smartmart.productservice.util.ProductUtil;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	private ProductRepository productRepository;

	@Override
	public void addProduct(ProductDto productDto) {
	    log.info("Validating product: {}", productDto.getName());
	    if (!ProductUtil.isValidProduct(productDto)) {
	        log.error("Product validation failed: name or price is null/empty");
	        throw new IllegalArgumentException("Product name and price cannot be null or empty");
	    }

	    Product product = Product.builder()
	            .name(productDto.getName())
	            .description(productDto.getDescription())
	            .price(productDto.getPrice())
	            .category(productDto.getCategory())
	            .createdAt(LocalDateTime.now())
	            .build();

	    productRepository.save(product);
	    log.info("Product saved successfully: {}", product.getName());
	}

	@Override
	@Transactional(readOnly = true)
	public Product getProductById(Integer id) {
	    log.debug("Fetching product with id: {}", id);
	    Product product = productRepository.findById(id)
	            .orElseThrow(() -> {
	                log.error("Product not found for id: {}", id);
	                return new RuntimeException("Product not found for id: " + id);
	            });
	    log.debug("Product found with id: {}: {}", id, product.getName());
	    return product;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> getAllProducts() {
	    log.debug("Fetching all products");
	    List<Product> products = productRepository.findAll();
	    log.info("Retrieved {} products from database", products.size());
	    return products;
	}

}

package com.smartmart.productservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartmart.productservice.dto.ProductDto;

public class ProductUtil {

	private static final Logger log = LoggerFactory.getLogger(ProductUtil.class);

	/**
	 * Validates that product name and price are not null
	 * 
	 * @param product the product object to validate
	 * @return true if both name and price are not null, false otherwise
	 */
	public static boolean isValidProduct(ProductDto product) {
	    if (product == null) {
	        log.error("Product object is null");
	        return false;
	    }
	    
	    boolean isValid = product.getName() != null && !product.getName().trim().isEmpty()
	            && product.getPrice() != null && product.getPrice() > 0
	            && product.getCategory() != null && !product.getCategory().trim().isEmpty();
	    
	    if (!isValid) {
	        log.error("Product validation failed - Name: {}, Price: {}, Category: {}", 
	                product.getName(), product.getPrice(), product.getCategory());
	    }
	    
	    return isValid;
	}

	/**
	 * Checks if product name is not null and not empty
	 * 
	 * @param name the product name to validate
	 * @return true if name is not null and not empty, false otherwise
	 */
	public static boolean isValidName(String name) {
		boolean isValid = name != null && !name.trim().isEmpty();
		if (!isValid) {
			log.error("Invalid product name: {}", name);
		}
		return isValid;
	}

	/**
	 * Checks if product price is not null and greater than 0
	 * 
	 * @param price the product price to validate
	 * @return true if price is not null and greater than 0, false otherwise
	 */
	public static boolean isValidPrice(Double price) {
		boolean isValid = price != null && price > 0;
		if (!isValid) {
			log.error("Invalid product price: {}", price);
		}
		return isValid;
	}

}


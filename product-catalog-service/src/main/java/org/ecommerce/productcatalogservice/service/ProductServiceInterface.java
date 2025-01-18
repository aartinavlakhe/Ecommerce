package org.ecommerce.productcatalogservice.service;

import org.ecommerce.productcatalogservice.dto.ProductResponseDto;
import org.ecommerce.productcatalogservice.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface ProductServiceInterface {
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product product);
    Product createProduct(Product product);
    Product deleteProduct(Long id);
    List<Product> getProductsByCategory(Long categoryId);
    List<Product> searchProducts(String keyword);
}

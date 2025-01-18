package org.ecommerce.productcatalogservice.service;

import org.ecommerce.productcatalogservice.model.Product;

import java.util.List;


public interface ProductServiceInterface {
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product product);
    Product createProduct(Product product);
    Product deleteProduct(Long id);
}

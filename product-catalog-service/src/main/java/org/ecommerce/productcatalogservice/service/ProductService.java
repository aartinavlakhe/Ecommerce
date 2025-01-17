package org.ecommerce.productcatalogservice.service;

import org.ecommerce.productcatalogservice.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product getProductById(UUID id);
    List<Product> getAllProducts();
    Product updateProduct(UUID id, Product product);
    Product replaceProduct();
    Product createProduct(Product product);
    Product deleteProduct();
}

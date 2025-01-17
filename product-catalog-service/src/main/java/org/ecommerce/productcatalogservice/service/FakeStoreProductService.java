package org.ecommerce.productcatalogservice.service;

import org.ecommerce.productcatalogservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {
    @Override
    public Product getProductById(UUID id) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product updateProduct(UUID id, Product product) {
        return null;
    }

    @Override
    public Product replaceProduct() {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product deleteProduct() {
        return null;
    }
}

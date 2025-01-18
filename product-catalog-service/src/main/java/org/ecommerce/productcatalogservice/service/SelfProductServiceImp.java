package org.ecommerce.productcatalogservice.service;

import org.ecommerce.productcatalogservice.exception.CategoryNotFoundException;
import org.ecommerce.productcatalogservice.exception.ProductExistException;
import org.ecommerce.productcatalogservice.exception.ProductNotFoundException;
import org.ecommerce.productcatalogservice.model.Category;
import org.ecommerce.productcatalogservice.model.Product;
import org.ecommerce.productcatalogservice.repository.CategoryRepository;
import org.ecommerce.productcatalogservice.repository.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("selfProductService")
@Primary
public class SelfProductServiceImp implements ProductServiceInterface {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductServiceImp(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Category category = categoryRepository.findByCategoryName(product.getProductCategory().getCategoryName())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        existProduct.setProductName(product.getProductName());
        existProduct.setProductDescription(product.getProductDescription());
        existProduct.setProductPrice(product.getProductPrice());
        existProduct.setProductCategory(category);

        return productRepository.save(existProduct);
    }

    @Override
    public Product createProduct(Product productRequest) {
        Category category = categoryRepository.findByCategoryName(productRequest.getProductCategory().getCategoryName())
               .orElseThrow(() -> new ProductNotFoundException("Category not found"));

        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setProductPrice(productRequest.getProductPrice());
        product.setProductCategory(category);

        return productRepository.save(product);
    }

    @Override
    public Product deleteProduct(Long id) {
//        if (!productRepository.existsById(id)) {
//            throw new ProductNotFoundException("Product not found");
//        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        productRepository.deleteById(id);
        return product;
    }

    /**
     * Fetch products by category ID.
     */
    public List<Product> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByproductCategory_Id(categoryId);
        return products;
    }

    /**
     * Search products by keyword.
     */
    public List<Product> searchProducts(String keyword) {
        List<Product> products = productRepository.searchProductsByKeyword(keyword);
        return products;
    }
}

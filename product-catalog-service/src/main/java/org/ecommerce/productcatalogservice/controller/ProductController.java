package org.ecommerce.productcatalogservice.controller;

import org.ecommerce.productcatalogservice.dto.ProductResponseDto;
import org.ecommerce.productcatalogservice.mapper.ProductMapper;
import org.ecommerce.productcatalogservice.model.Product;
import org.ecommerce.productcatalogservice.service.ProductServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductServiceInterface productService;

    public ProductController(ProductServiceInterface productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") Long id) {
        ProductResponseDto productResponse = ProductMapper.toProductResponseDto(
                productService.getProductById(id)
        );
         return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> productResponse = productService.getAllProducts()
                .stream()
                .map(ProductMapper::toProductResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        ProductResponseDto productResponse = ProductMapper.toProductResponseDto(
                productService.updateProduct(id, product)
        );
        return ResponseEntity.ok(productResponse);
    }

    @PostMapping("/")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody Product product) {
        ProductResponseDto productResponse = ProductMapper.toProductResponseDto(
                productService.createProduct(product)
        );
        return new ResponseEntity<>(productResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable("id") Long id) {
        ProductResponseDto productResponse = ProductMapper.toProductResponseDto(
                productService.deleteProduct(id)
        );

        return ResponseEntity.ok(productResponse);
    }

    /**
     * Get products by category.
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductResponseDto> productResponse = productService.getProductsByCategory(categoryId)
                                                    .stream()
                                                    .map(ProductMapper::toProductResponseDto)
                                                    .collect(Collectors.toList());
        return ResponseEntity.ok(productResponse);
    }

    /**
     * Search products by keyword.
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProducts(@RequestParam("productName") String keyword) {
        List<ProductResponseDto> productResponse = productService.searchProducts(keyword)
                                                    .stream()
                                                    .map(ProductMapper::toProductResponseDto)
                                                    .collect(Collectors.toList());
        return ResponseEntity.ok(productResponse);
    }

}

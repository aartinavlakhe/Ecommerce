package org.ecommerce.productcatalogservice.service;

import org.ecommerce.productcatalogservice.exception.ProductNotFoundException;
import org.ecommerce.productcatalogservice.model.Category;
import org.ecommerce.productcatalogservice.model.Product;
import org.ecommerce.productcatalogservice.repository.CategoryRepository;
import org.ecommerce.productcatalogservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SelfProductServiceImpTest {
    @InjectMocks
    private SelfProductServiceImp productService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;

    private Product product;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        // Sample category
        category = new Category();
        category.setId(1L);
        category.setCategoryName("Test Category");

        // Sample product
        product = new Product();
        product.setId(1L);
        product.setProductName("Test Product");
        product.setProductDescription("Test Product Description");
        product.setProductCategory(category);
        product.setProductPrice(1000.0);
    }

    @Test
    void testGetProductById_Success() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Product result = productService.getProductById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getProductName(), result.getProductName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_ProductNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(1L);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllProducts_Success() {
        // Arrange
        when(productRepository.findAll()).thenReturn(List.of(product));

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(product.getProductName(), result.get(0).getProductName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testCreateProduct_Success() {
        // Arrange
        when(categoryRepository.findByCategoryName(any())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product result = productService.createProduct(product);

        // Assert
        assertNotNull(result);
        assertEquals(product.getProductName(), result.getProductName());
        assertEquals(product, result);
        verify(categoryRepository, times(1)).findByCategoryName(category.getCategoryName());
//        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProduct_Success() {
        // Arrange
        when(categoryRepository.findByCategoryName(any())).thenReturn(Optional.of(category));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product result = productService.updateProduct(1L, product);

        // Assert
        assertNotNull(result);
        assertEquals(product.getProductName(), result.getProductName());
        verify(categoryRepository, times(1)).findByCategoryName(category.getCategoryName());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProduct_NotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(1L, product);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteProduct_Success() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(1L);

        // Act
        Product result = productService.deleteProduct(1L);

        // Assert
        assertEquals(product, result);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProduct_NotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteProduct(1L);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }
}

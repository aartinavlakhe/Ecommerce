package org.ecommerce.productcatalogservice.controller;

import org.ecommerce.productcatalogservice.dto.ProductResponseDto;
import org.ecommerce.productcatalogservice.exception.ProductNotFoundException;
import org.ecommerce.productcatalogservice.model.Category;
import org.ecommerce.productcatalogservice.model.Product;
import org.ecommerce.productcatalogservice.service.ProductServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductControllerTest {
    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductServiceInterface productService;

    private Product product;
    private Category category;
    private ProductResponseDto productResponseDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        category = new Category();
        category.setId(1L);
        category.setCategoryName("Test Product Category");

        product = new Product();
        product.setId(1L);
        product.setProductName("Test Product");
        product.setProductDescription("Test Product Description");
        product.setProductCategory(category);
        product.setProductPrice(1.0);

        productResponseDto = new ProductResponseDto();
        productResponseDto.setProductName("iPhone");
        productResponseDto.setProductDescription("Brand new iPhone with awesome features");
        productResponseDto.setProductCategory("Mobile Phone");
        productResponseDto.setProductPrice(200.0);
    }

    @Test
    public void testGetProductById_Success() throws Exception {
        // Arrange
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(product);

        // Act
        ResponseEntity<ProductResponseDto> response = productController.getProductById(productId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Product", response.getBody().getProductName());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    public void testGetAllProducts_Success() throws Exception {
        // Arrange
        List<Product> mockProducts = Arrays.asList(product);
        when(productService.getAllProducts()).thenReturn(mockProducts);

        // Act
        ResponseEntity<List<ProductResponseDto>> response = productController.getAllProducts();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Product", response.getBody().get(0).getProductName());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void testCreateProduct_success() {
        // Arrange
        when(productService.createProduct(product)).thenReturn(product);

        // Act
        ResponseEntity<ProductResponseDto> response = productController.createProduct(product);

        // Assert
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Test Product", response.getBody().getProductName());
        verify(productService, times(1)).createProduct(product);
    }

    @Test
    public void testUpdateProduct_Success() throws Exception {
        // Arrange
        Long productId = 1L;
        when(productService.updateProduct(productId, product)).thenReturn(product);

        // Act
        ResponseEntity<ProductResponseDto> response = productController.updateProduct(productId, product);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Product", response.getBody().getProductName());
        verify(productService, times(1)).updateProduct(productId, product);
    }

    @Test
    public void testDeleteProduct_Success() throws Exception {
        // Arrange
        Long productId = 1L;
        when(productService.deleteProduct(productId)).thenReturn(product);

        // Act
        ResponseEntity<ProductResponseDto> response = productController.deleteProduct(productId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Product", response.getBody().getProductName());
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    public void testGetProductsByCategory_Success() throws Exception {
        // Arrange
        Long categoryId = 1L;
        List<Product> mockProducts = Arrays.asList(product);
        when(productService.getProductsByCategory(categoryId)).thenReturn(mockProducts);

        // Act
        ResponseEntity<List<ProductResponseDto>> response = productController.getProductsByCategory(categoryId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Product", response.getBody().get(0).getProductName());
        verify(productService, times(1)).getProductsByCategory(categoryId);
    }

    @Test
    public void testSearchProducts_Success() throws Exception {
        // Arrange
        String keyword = "Test";
        List<Product> mockProducts = Arrays.asList(product);
        when(productService.searchProducts(keyword)).thenReturn(mockProducts);

        // Act
        ResponseEntity<List<ProductResponseDto>> response = productController.searchProducts(keyword);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Product", response.getBody().get(0).getProductName());
        verify(productService, times(1)).searchProducts(keyword);
    }

    @Test
    public void testGetProductById_ProductNotFound() throws Exception {
        // Arrange
        Long productId = 999L; // Nonexistent ID
        when(productService.getProductById(productId)).thenThrow(new ProductNotFoundException("Product not found"));

        // Act & Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productController.getProductById(productId);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    public void testGetAllProducts_EmptyList() throws Exception {
        // Arrange
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<ProductResponseDto>> response = productController.getAllProducts();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
        verify(productService, times(1)).getAllProducts();
    }

//    @Test
//    public void testCreateProduct_InvalidInput() throws Exception {
//        // Arrange
//        product.setProductName(""); // Invalid name
//        product.setProductPrice(null); // Missing price
//
//        // Act & Assert
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            productController.createProduct(product);
//        });
//
//        assertEquals("Invalid input data", exception.getMessage());
//        verify(productService, never()).createProduct(any());
//    }

    @Test
    public void testUpdateProduct_ProductNotFound() throws Exception {
        // Arrange
        Long productId = 999L; // Nonexistent ID
        when(productService.updateProduct(eq(productId), any(Product.class)))
                .thenThrow(new ProductNotFoundException("Product not found"));

        // Act & Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productController.updateProduct(productId, product);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productService, times(1)).updateProduct(eq(productId), any(Product.class));
    }

    @Test
    public void testDeleteProduct_ProductNotFound() throws Exception {
        // Arrange
        Long productId = 999L; // Nonexistent ID
        doThrow(new ProductNotFoundException("Product not found")).when(productService).deleteProduct(productId);

        // Act & Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productController.deleteProduct(productId);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    public void testSearchProducts_EmptyResult() throws Exception {
        // Arrange
        String keyword = "Nonexistent";
        when(productService.searchProducts(keyword)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<ProductResponseDto>> response = productController.searchProducts(keyword);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
        verify(productService, times(1)).searchProducts(keyword);
    }

}

package org.ecommerce.productcatalogservice.controller;

import org.ecommerce.productcatalogservice.dto.CategoryResponseDto;
import org.ecommerce.productcatalogservice.exception.CategoryNotFoundException;
import org.ecommerce.productcatalogservice.exception.ProductNotFoundException;
import org.ecommerce.productcatalogservice.model.Category;
import org.ecommerce.productcatalogservice.service.CategoryServiceInterface;
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

public class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryServiceInterface categoryService;

    private Category category;
    private CategoryResponseDto categoryResponseDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        category = new Category();
        category.setId(1L);
        category.setCategoryName("Test Category");

        categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setCategoryName("Test Category");
    }

    @Test
    public void testGetCategoryById_Success() throws Exception {
        // Arrange
        Long categoryId = 1L;
        when(categoryService.getCategoryById(categoryId)).thenReturn(category);

        // Act
        ResponseEntity<CategoryResponseDto> response = categoryController.getCategoryById(categoryId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Category", response.getBody().getCategoryName());
        verify(categoryService, times(1)).getCategoryById(categoryId);
    }

    @Test
    public void testGetAllCategory_Success() throws Exception {
        // Arrange
        List<Category> mockCategory = Arrays.asList(category);
        when(categoryService.getAllCategory()).thenReturn(mockCategory);

        // Act
        ResponseEntity<List<CategoryResponseDto>> response = categoryController.getAllCategory();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Category", response.getBody().get(0).getCategoryName());
        verify(categoryService, times(1)).getAllCategory();
    }

    @Test
    public void testCreateCategory_success() {
        // Arrange
        when(categoryService.createCategory(category)).thenReturn(category);

        // Act
        ResponseEntity<CategoryResponseDto> response = categoryController.createCategory(category);

        // Assert
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Test Category", response.getBody().getCategoryName());
        verify(categoryService, times(1)).createCategory(category);
    }

    @Test
    public void testGetCategoryById_CategoryNotFound() throws Exception {
        // Arrange
        Long categoryId = 999L; // Nonexistent ID
        when(categoryService.getCategoryById(categoryId)).thenThrow(new CategoryNotFoundException("Category not found"));

        // Act & Assert
        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> {
            categoryController.getCategoryById(categoryId);
        });

        assertEquals("Category not found", exception.getMessage());
        verify(categoryService, times(1)).getCategoryById(categoryId);
    }

    @Test
    public void testGetAllCategory_EmptyList() throws Exception {
        // Arrange
        when(categoryService.getAllCategory()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<CategoryResponseDto>> response = categoryController.getAllCategory();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
        verify(categoryService, times(1)).getAllCategory();
    }

//    @Test
//    public void testCreateCategory_InvalidInput() throws Exception {
//        // Arrange
//        product.setCategoryName(""); // Invalid name
//
//        // Act & Assert
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            categoryController.createPCategory(category);
//        });
//
//        assertEquals("Invalid input data", exception.getMessage());
//        verify(categoryService, never()).createCategory(any());
//    }
}

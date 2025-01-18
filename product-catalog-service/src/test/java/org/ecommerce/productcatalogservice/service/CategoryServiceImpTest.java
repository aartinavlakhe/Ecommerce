package org.ecommerce.productcatalogservice.service;

import org.ecommerce.productcatalogservice.exception.CategoryNotFoundException;
import org.ecommerce.productcatalogservice.model.Category;
import org.ecommerce.productcatalogservice.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceImpTest {
    @InjectMocks
    private CategoryServiceImp categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        category = new Category();
        category.setId(1L);
        category.setCategoryName("Test Category");
    }

    @Test
    void testGetCategoryById_Success() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Act
        Category result = categoryService.getCategoryById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(category.getCategoryName(), result.getCategoryName());
        assertEquals(category, result);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCategoryById_NotFound() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.getCategoryById(1L);
        });
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllCategory_Success() {
        // Arrange
        List<Category> categories = Arrays.asList(category);
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<Category> result = categoryService.getAllCategory();

        // Assert
        assertNotNull(result); assertEquals(1, result.size());
        assertEquals(categories, result);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testCreateCategory_Success() {
        // Arrange
        when(categoryRepository.findByCategoryName(anyString())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // Act
        Category result = categoryService.createCategory(category);

        // Assert
        assertNotNull(result);
        assertEquals(category.getCategoryName(), result.getCategoryName());
        verify(categoryRepository, times(1)).findByCategoryName(anyString());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testCreateCategory_CategoryExists() {
        // Arrange
        when(categoryRepository.findByCategoryName(anyString())).thenReturn(Optional.of(category));

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.createCategory(category);
        });
        verify(categoryRepository, times(1)).findByCategoryName(anyString());
        verify(categoryRepository, times(0)).save(any(Category.class));
    }
}

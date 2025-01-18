package org.ecommerce.productcatalogservice.controller;

import org.ecommerce.productcatalogservice.dto.CategoryResponseDto;
import org.ecommerce.productcatalogservice.mapper.CategoryMapper;
import org.ecommerce.productcatalogservice.model.Category;
import org.ecommerce.productcatalogservice.service.CategoryServiceInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryServiceInterface categoryService;
    public CategoryController(CategoryServiceInterface categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getCategoryById(@PathVariable("id") Long id) {
        return CategoryMapper.toCategoryResponseDto(
                categoryService.getCategoryById(id)
        );
    }

    @GetMapping("/")
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories()
                .stream()
                .map(CategoryMapper::toCategoryResponseDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public CategoryResponseDto createCategory(@RequestBody Category category) {
        return CategoryMapper.toCategoryResponseDto(
                categoryService.createCategory(category)
        );
    }
}

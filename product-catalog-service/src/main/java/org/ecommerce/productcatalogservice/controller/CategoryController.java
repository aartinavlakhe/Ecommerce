package org.ecommerce.productcatalogservice.controller;

import org.ecommerce.productcatalogservice.dto.CategoryResponseDto;
import org.ecommerce.productcatalogservice.mapper.CategoryMapper;
import org.ecommerce.productcatalogservice.model.Category;
import org.ecommerce.productcatalogservice.service.CategoryServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryServiceInterface categoryService;
    public CategoryController(CategoryServiceInterface categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable("id") Long id) {
        CategoryResponseDto responseDto = CategoryMapper.toCategoryResponseDto(
                categoryService.getCategoryById(id)
        );

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategory() {
         List<CategoryResponseDto> responseDto = categoryService.getAllCategory()
                .stream()
                .map(CategoryMapper::toCategoryResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody Category category) {
         CategoryResponseDto responseDto = CategoryMapper.toCategoryResponseDto(
                categoryService.createCategory(category)
        );
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}

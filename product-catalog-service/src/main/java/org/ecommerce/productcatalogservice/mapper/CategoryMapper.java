package org.ecommerce.productcatalogservice.mapper;

import org.ecommerce.productcatalogservice.dto.CategoryResponseDto;
import org.ecommerce.productcatalogservice.model.Category;

import java.util.Optional;

public class CategoryMapper {
    public static CategoryResponseDto toCategoryResponseDto(Category category) {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setCategoryName(category.getCategoryName());

        return categoryResponseDto;
    }
}

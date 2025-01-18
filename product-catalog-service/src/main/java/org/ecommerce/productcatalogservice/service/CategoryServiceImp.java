package org.ecommerce.productcatalogservice.service;

import org.ecommerce.productcatalogservice.exception.CategoryNotFoundException;
import org.ecommerce.productcatalogservice.model.Category;
import org.ecommerce.productcatalogservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryServiceInterface{

    private CategoryRepository categoryRepository;

    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public Category createCategory(Category category) {
        Optional<Category> existCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (existCategory.isPresent()) {
            throw new CategoryNotFoundException("Category Already Exists");
        }

        Category categoryObj = new Category();
        categoryObj.setCategoryName(category.getCategoryName());

        return categoryRepository.save(categoryObj);
    }
}

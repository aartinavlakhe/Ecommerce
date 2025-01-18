package org.ecommerce.productcatalogservice.service;

import org.ecommerce.productcatalogservice.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryServiceInterface {
    public Category getCategoryById(Long id);
    public List<Category> getAllCategory();
    public Category createCategory(Category category);
}

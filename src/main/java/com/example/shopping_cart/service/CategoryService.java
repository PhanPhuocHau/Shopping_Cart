package com.example.shopping_cart.service;
import java.util.List;

import com.example.shopping_cart.model.Category;

public interface CategoryService {
    public Category saveCategory(Category category);
    
    public List<Category> getAllCategories();
    public boolean existCategory(String name);
}

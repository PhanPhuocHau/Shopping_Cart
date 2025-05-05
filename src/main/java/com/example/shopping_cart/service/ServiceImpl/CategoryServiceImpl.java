package com.example.shopping_cart.service.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.shopping_cart.model.Category;
import com.example.shopping_cart.repository.CategoryRepository;
import com.example.shopping_cart.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        if (ObjectUtils.isEmpty(category)) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Boolean existCategory(String name) {
        if (ObjectUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        return categoryRepository.existsByName(name);
    }

    @Override
    public Boolean deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElse(null);

        if (!ObjectUtils.isEmpty(category)) {
            categoryRepository.delete(category);
            return true;
        }
        return false;
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> getAllActiveCategory() {
        List<Category> categories  =categoryRepository.findByIsActiveTrue();
        return categories;
    }
}
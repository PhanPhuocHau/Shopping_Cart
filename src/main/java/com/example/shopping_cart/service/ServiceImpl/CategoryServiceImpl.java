package com.example.shopping_cart.service.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopping_cart.model.Category;
import com.example.shopping_cart.repository.CategoryRepository;
import com.example.shopping_cart.service.CategoryService;
import org.springframework.util.ObjectUtils;

@Service
public  class CategoryServiceImpl implements CategoryService {
    @Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public boolean existCategory(String name) {
    	return categoryRepository.existsByName(name); // Kiểm tra danh mục đã tồn tại
}
	@Override
	public Boolean deleteCategory(int id) {
	Category category = categoryRepository.findById(id).orElse(null);

		if (!ObjectUtils.isEmpty(category)){
			categoryRepository.delete(category);
			return true;
		}
		return false;
		}
}
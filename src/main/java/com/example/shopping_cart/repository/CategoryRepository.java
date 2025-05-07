package com.example.shopping_cart.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping_cart.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);

    public List<Category> findByIsActiveTrue();
}


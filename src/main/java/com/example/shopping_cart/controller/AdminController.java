package com.example.shopping_cart.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.shopping_cart.model.Category;
import com.example.shopping_cart.service.CategoryService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct() {
        return "admin/add_product";
    }

    @GetMapping("/category")
    public String category() {
        return "admin/category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(
            @ModelAttribute Category category,
            @RequestParam("file") MultipartFile file,
            HttpSession session) throws IOException {

        // Xử lý tên ảnh
        String imageName = (file != null && !file.isEmpty()) ? file.getOriginalFilename() : "default.jpg";
        category.setImageName(imageName);

        // Kiểm tra danh mục đã tồn tại
        boolean categoryExists = categoryService.existCategory(category.getName());
        if (categoryExists) {
            session.setAttribute("errorMsg", "Category name already exists!");
            return "redirect:/admin/category";
        }

        // Lưu danh mục
        Category savedCategory = categoryService.saveCategory(category);
        if (savedCategory == null) {
            session.setAttribute("errorMsg", "Failed to save category!");
        } else {
            session.setAttribute("succMsg", "Category added successfully!");
        }

        return "redirect:/admin/category";
    }
}
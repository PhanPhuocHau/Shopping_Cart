package com.example.shopping_cart.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.example.shopping_cart.model.Product;
import com.example.shopping_cart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
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

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct(Model m) {
        List<Category> categories =categoryService.getAllCategories();
        m.addAttribute("categories", categories);
        return "admin/add_product";
    }

    @GetMapping("/category")
    public String category(Model m) {
        m.addAttribute("categorys", categoryService.getAllCategories());
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

    public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image, HttpSession session)throws IOException {

        String imageName = image.isEmpty()?"default.jpg":image.getOriginalFilename();

        product.setImage(imageName);

        Product savedProduct = productService.saveProduct(product);
        if(!ObjectUtils.isEmpty(savedProduct)){
            File saveFile = new ClassPathResource("static/img").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator + image.getOriginalFilename());
            System.out.println(path);
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            session.setAttribute("succMsg", "Product Saved Success");
        }else {
            session.setAttribute("errorMsg", "Something wrong on server");
        }

        return "redirect:/admin/loadAddProduct";

    }
    @GetMapping("/products")
    public String LoadViewProduct(Model m){
        return "admin/products";
    }
}
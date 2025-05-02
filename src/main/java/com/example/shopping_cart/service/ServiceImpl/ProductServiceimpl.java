package com.example.shopping_cart.service.ServiceImpl;

import com.example.shopping_cart.model.Product;
import com.example.shopping_cart.repository.ProductRepository;
import com.example.shopping_cart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceimpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }



}

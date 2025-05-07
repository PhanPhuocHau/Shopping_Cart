package com.example.shopping_cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping_cart.model.Cart;
public interface CartRepository extends JpaRepository<Cart, Integer> {

	public Cart findByProductIdAndUserId(Integer productId, Integer userId);

}
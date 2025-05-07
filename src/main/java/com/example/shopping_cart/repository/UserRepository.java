package com.example.shopping_cart.repository;

import com.example.shopping_cart.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {

    public UserDtls findByEmail(String email);

}

package com.example.shopping_cart.config;

import com.example.shopping_cart.model.UserDtls;
import com.example.shopping_cart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDtls user = userRepository.findByEmail(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUser(user);
    }
}

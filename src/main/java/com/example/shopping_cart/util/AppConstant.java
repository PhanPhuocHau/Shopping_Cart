package com.example.shopping_cart.util;

public class AppConstant {
    // Số lần đăng nhập thất bại tối đa trước khi khóa tài khoản
    public static final int ATTEMPT_TIME = 3;
    
    // Thời gian khóa tài khoản (24 giờ tính bằng milliseconds)
    public static final long UNLOCK_DURATION_TIME = 24 * 60 * 60 * 1000;
}
package com.example.shopping_cart.service;

import java.util.List;

import com.example.shopping_cart.model.OrderRequest;
import com.example.shopping_cart.model.ProductOrder;

public interface OrderService {

	public void saveOrder(Integer userid, OrderRequest orderRequest) throws Exception;

	public List<ProductOrder> getOrdersByUser(Integer userId);

	public ProductOrder updateOrderStatus(Integer id, String status);

	public List<ProductOrder> getAllOrders();
}
package com.example.food.service;

import com.example.food.dto.Request.OrderRequest.CreateOrderRequest;
import com.example.food.dto.Response.OrderResponse.OrderResponse;
import com.example.food.model.Order;

import java.util.List;

public interface OrderService {
    public List<Order> getAllOrder();
    public List<Order> getOrderOfUser(int customerId);
    public OrderResponse createOrder(CreateOrderRequest request);
}

package com.example.food.repository;

import com.example.food.model.Cart;
import com.example.food.model.Food;
import com.example.food.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Integer> {
    Optional<Cart> findCartByUserAndFood(User user, Food food);
}

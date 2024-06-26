package com.example.food.repository;

import com.example.food.model.Cart;
import com.example.food.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsersID(int userid);
}

package com.example.food.cotroller;

import com.example.food.dto.Request.CategoryRequest.CreateCategoryRequest;
import com.example.food.dto.Request.FoodRequest.CreateFoodRequest;
import com.example.food.dto.Request.FoodRequest.UpdateFoodRequest;
import com.example.food.dto.Response.CategoryResponse.CategoryResponse;
import com.example.food.dto.Response.FoodResponse.FoodResponse;
import com.example.food.dto.Response.UserResponse.UserResponse;
import com.example.food.model.Food;
import com.example.food.model.User;
import com.example.food.service.FoodService;
import com.example.food.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/food")
@RequiredArgsConstructor
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("")
//    @PreAuthorize("hasAuthority('admin:read')")
    public List<Food> getAllUsers() {
        return foodService.getAll();
    }
    @GetMapping("{id}")
//    @PreAuthorize("hasAuthority('admin:read')")
    public Optional<Food> getUserById(@PathVariable int id) {
        return foodService.getFoodByID(id);
    }
    @PostMapping("")
    //    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<FoodResponse> createFood(
            @RequestParam("foodName") String foodName,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("category") int category,
            @RequestParam("quantity") int quantity,
            @RequestParam("file") MultipartFile file) {
        CreateFoodRequest food = CreateFoodRequest.builder()
                .foodName(foodName)
                .description(description)
                .price(price)
                .category(category)
                .quantity(quantity)
                .build();
        FoodResponse response = foodService.createFood(food,file);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<FoodResponse> updateFood(
            @PathVariable int id,
            @RequestParam("foodName") String foodName,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("category") int category,
            @RequestParam("quantity") int quantity,
            @RequestParam("file") MultipartFile file) {
        UpdateFoodRequest food = UpdateFoodRequest.builder()
                .foodName(foodName)
                .description(description)
                .price(price)
                .category(category)
                .quantity(quantity)
                .build();
        FoodResponse response = foodService.updateFood(id,food,file);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FoodResponse> deleteFood(@PathVariable int id) {
        FoodResponse response = foodService.deleteFood(id);
        return ResponseEntity.ok(response);
    }
}

package com.example.food.Service.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.food.Service.IService.IFoodService;
import com.example.food.ViewModel.Request.FoodRequest.CreateFoodRequest;
import com.example.food.ViewModel.Request.FoodRequest.UpdateFoodRequest;
import com.example.food.ViewModel.Response.FoodResponse.FoodResponse;
import com.example.food.Model.Food;
import com.example.food.Repository.CategoryRepo;
import com.example.food.Repository.FoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FoodService implements IFoodService {
    @Autowired
    private FoodRepo foodRepo;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<Food> getAll() {
        return foodRepo.findAll();
    }

    @Override
    public Optional<Food> getFoodByID(int id) {
        return foodRepo.findFoodByFoodID(id);
    }

    @Override
    public FoodResponse createFood(CreateFoodRequest request, MultipartFile file) {
        int category = request.getCategory();
        //check category exits or not
        var cate = categoryRepo.findCategoriesByCategoryId(category).orElse(null);
        if (cate == null) {
            return FoodResponse.builder()
                    .status("Category not found")
                    .food(null)
                    .build();
        } else {
            String name = request.getFoodName();
            String description = request.getDescription();
            double price = request.getPrice();
            LocalDateTime postedAt = LocalDateTime.now();
            String foodImage = uploadImageToCloudinary(file);
            int quantity = request.getQuantity();
            // check food exist or not by name
            var food = foodRepo.findFoodByFoodName(name).orElse(null);
            if (food == null) {
                Food food1 = Food.builder()
                        .foodName(name)
                        .foodImage(foodImage)
                        .price(price)
                        .category(cate)
                        .description(description)
                        .dateAt(postedAt)
                        .quantity(quantity)
                        .foodStatus(true)
                        .build();
                foodRepo.save(food1);
                return FoodResponse.builder()
                        .status("Create Food Successfully")
                        .food(food1)
                        .build();
            } else {
                return FoodResponse.builder()
                        .status("Food already exists")
                        .food(null)
                        .build();
            }
        }

    }

    @Override
    public FoodResponse updateFood(int foodId, UpdateFoodRequest request, MultipartFile file) {
        int category = request.getCategory();
        //check category exits or not
        var cate = categoryRepo.findCategoriesByCategoryId(category).orElse(null);
        if (cate == null) {
            return FoodResponse.builder()
                    .status("Category not found")
                    .food(null)
                    .build();
        }
        // check food exist or not by id
        var food = foodRepo.findFoodByFoodID(foodId).orElse(null);
        if (food != null) {
            String name = request.getFoodName();
            String description = request.getDescription();
            double price = request.getPrice();
            int quantity = request.getQuantity();
            String foodImage = uploadImageToCloudinary(file);
            food.setFoodName(name);
            food.setFoodImage(foodImage);
            food.setCategory(cate);
            food.setPrice(price);
            food.setQuantity(quantity);
            food.setDescription(description);
            foodRepo.save(food);
            return FoodResponse.builder()
                    .status("Update Food Successfully")
                    .food(food)
                    .build();

        } else {
            return FoodResponse.builder()
                    .status("Food not found")
                    .food(null)
                    .build();
        }

    }

    @Override
    public FoodResponse deleteFood(int foodId) {
        var food = foodRepo.findFoodByFoodID(foodId).orElse(null);
        if (food == null) {
            return FoodResponse.builder()
                    .status("Food not found")
                    .food(null)
                    .build();
        }else{
            foodRepo.delete(food);
            return FoodResponse.builder()
                    .status("Food deleted successfully")
                    .food(null)
                    .build();
        }
    }

    public String uploadImageToCloudinary(MultipartFile file) {
        try {
            // Upload image to Cloudinary
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            // Get the URL of the uploaded image from Cloudinary response
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.driver.service.impl;


import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FoodServiceImpl implements FoodService {
    @Autowired
    FoodRepository foodRepository;
    @Override
    public FoodDto createFood(FoodDto food) {
        FoodEntity foodEntity=new FoodEntity();
        foodEntity.setFoodCategory(food.getFoodCategory());
        foodEntity.setFoodId(food.getFoodId());
        foodEntity.setFoodName(food.getFoodName());
        foodEntity.setFoodPrice(food.getFoodPrice());

        foodRepository.save(foodEntity);

        return food;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        return null;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {
        FoodEntity foodEntity= foodRepository.findByFoodId(foodId);
        foodEntity.setFoodName(foodDetails.getFoodName());
        foodEntity.setFoodPrice(foodDetails.getFoodPrice());
        foodEntity.setFoodCategory(foodDetails.getFoodCategory());


        return foodDetails;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {
        FoodEntity foodEntity=foodRepository.findByFoodId(id);
        foodRepository.deleteById(foodEntity.getId());

    }

    @Override
    public List<FoodDto> getFoods() {
        return null;
    }
}

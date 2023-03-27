package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Override
    public FoodDto createFood(FoodDto food) {

        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setFoodCategory(food.getFoodCategory());
        foodEntity.setFoodId(food.getFoodId());
        foodEntity.setFoodName(food.getFoodName());
        foodEntity.setFoodPrice(food.getFoodPrice());

        foodRepository.save(foodEntity);

        return food;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {

        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);

        FoodDto foodDto = new FoodDto();
        foodDto.setFoodPrice(foodEntity.getFoodPrice());
        foodDto.setFoodName(foodEntity.getFoodName());
        foodDto.setFoodCategory(foodEntity.getFoodCategory());
        foodDto.setFoodId(foodEntity.getFoodId());
        return foodDto;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {

        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        foodEntity.setFoodPrice(foodDetails.getFoodPrice());
        foodEntity.setFoodName(foodDetails.getFoodName());
        foodEntity.setFoodCategory(foodDetails.getFoodCategory());
        foodEntity.setFoodId(foodDetails.getFoodId());

        foodRepository.save(foodEntity);

        return foodDetails;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {

        FoodEntity foodEntity = foodRepository.findByFoodId(id);
        long Id = foodEntity.getId();
        foodRepository.deleteById(Id);


    }

    @Override
    public List<FoodDto> getFoods() {

        List<FoodEntity>foodEntityList = (List<FoodEntity>) foodRepository.findAll();

        List<FoodDto>foodDtoList = new ArrayList<>();

        for(FoodEntity foodEntity:foodEntityList){
            FoodDto foodDto = new FoodDto();
            foodDto.setFoodId(foodEntity.getFoodId());
            foodDto.setFoodName(foodEntity.getFoodName());
            foodDto.setFoodPrice(foodEntity.getFoodPrice());
            foodDto.setFoodCategory(foodEntity.getFoodCategory());

            foodDtoList.add(foodDto);
        }


        return foodDtoList;
    }
}

package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
public class FoodController {
	@Autowired
	FoodService foodService;

	@Autowired
	FoodRepository foodRepository;


	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{
		FoodEntity foodEntity = foodRepository.findById(Long.parseLong(id)).get();
		String foodId = foodEntity.getFoodId();

		FoodDto foodDto = foodService.getFoodById(foodId);

		FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
		foodDetailsResponse.setFoodPrice(foodDto.getFoodPrice());
		foodDetailsResponse.setFoodName(foodDto.getFoodName());
		foodDetailsResponse.setFoodCategory(foodDto.getFoodCategory());
		foodDetailsResponse.setFoodId(foodDto.getFoodId());

		return foodDetailsResponse;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {
		FoodDto foodDto=new FoodDto();
		foodDto.setFoodId(UUID.randomUUID().toString());
		foodDto.setFoodCategory(foodDetails.getFoodCategory());
		foodDto.setFoodName(foodDetails.getFoodName());
		foodDto.setFoodPrice(foodDetails.getFoodPrice());

		FoodDto foodDto1=foodService.createFood(foodDto);

		//FOOD RESPONSE BODY

		FoodDetailsResponse foodDetailsResponse=new FoodDetailsResponse();
		foodDetailsResponse.setFoodCategory(foodDto1.getFoodCategory());
		foodDetailsResponse.setFoodName(foodDto1.getFoodName());
		foodDetailsResponse.setFoodPrice(foodDto1.getFoodPrice());
		foodDetailsResponse.setFoodId(foodDto1.getFoodId());

		return foodDetailsResponse;

	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
		FoodDto foodDto=new FoodDto();
		foodDto.setFoodName(foodDetails.getFoodName());
		foodDto.setFoodPrice(foodDetails.getFoodPrice());
		foodDto.setFoodPrice(foodDto.getFoodPrice());

		FoodDto foodDto1=foodService.updateFoodDetails(id,foodDto);


		//respnse
		FoodDetailsResponse foodDetailsResponse=new FoodDetailsResponse();
		foodDetailsResponse.setFoodId(foodDto1.getFoodId());
		foodDetailsResponse.setFoodPrice(foodDto1.getFoodPrice());
		foodDetailsResponse.setFoodName(foodDto1.getFoodName());
		foodDetailsResponse.setFoodCategory(foodDto1.getFoodCategory());

		return foodDetailsResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		OperationStatusModel operationStatusModel = new OperationStatusModel();

		try {
			foodService.deleteFoodItem(id);
			operationStatusModel.setOperationResult("Delete");
			operationStatusModel.setOperationResult("Success");
			return operationStatusModel;
		}catch (Exception e){
			operationStatusModel.setOperationResult("Delete");
			operationStatusModel.setOperationResult("Failed");
			return operationStatusModel;
		}
	}
	
	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {

		List<FoodDto>foodDtoList = foodService.getFoods();

		List<FoodDetailsResponse>foodDetailsResponseList = new ArrayList<>();

		for(FoodDto foodDto:foodDtoList){
			FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
			foodDetailsResponse.setFoodName(foodDto.getFoodName());
			foodDetailsResponse.setFoodPrice(foodDto.getFoodPrice());
			foodDetailsResponse.setFoodCategory(foodDto.getFoodCategory());
			foodDetailsResponse.setFoodId(foodDto.getFoodId());

			foodDetailsResponseList.add(foodDetailsResponse);
		}

		return foodDetailsResponseList;
	}
}

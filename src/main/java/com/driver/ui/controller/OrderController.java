package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
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
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	OrderService orderService;
	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{

		return null;
	}
	
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {
		OrderDto orderDto=new OrderDto();
		//orderDto.setOrderId(UUID.randomUUID().toString());
		orderDto.setCost(order.getCost());
		orderDto.setCost(order.getCost());
		orderDto.setUserId(order.getUserId());
		orderDto.setStatus(true);
		orderDto.setItems(order.getItems());

		OrderDto orderDto1 = orderService.createOrder(orderDto);

		OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
		orderDetailsResponse.setOrderId(orderDto1.getOrderId());
		orderDetailsResponse.setCost(orderDto1.getCost());
		orderDetailsResponse.setStatus(orderDto1.isStatus());
		orderDetailsResponse.setItems(orderDto1.getItems());
		orderDetailsResponse.setUserId(orderDto1.getUserId());

		return orderDetailsResponse;

		

	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{
		OrderDto orderDto = new OrderDto();
		orderDto.setUserId(order.getUserId());
		orderDto.setCost(order.getCost());
		orderDto.setItems(order.getItems());
		orderDto.setOrderId(UUID.randomUUID().toString());

		OrderDto orderDto1 = orderService.updateOrderDetails(id,orderDto);

		OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
		orderDetailsResponse.setOrderId(orderDto1.getOrderId());
		orderDetailsResponse.setStatus(orderDto1.isStatus());
		orderDetailsResponse.setCost(orderDto1.getCost());
		orderDetailsResponse.setItems(orderDto1.getItems());
		orderDetailsResponse.setUserId(orderDto1.getUserId());
		return orderDetailsResponse;
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {
		OperationStatusModel operationStatusModel = new OperationStatusModel();

		try {
			orderService.deleteOrder(id);
			operationStatusModel.setOperationResult("success");
			operationStatusModel.setOperationName("Delete");
			return operationStatusModel;
		}catch (Exception e){
			operationStatusModel.setOperationName("Delete");
			operationStatusModel.setOperationResult("Failed");
			return operationStatusModel;
		}
	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {
		List<OrderDto>orderDtoList = orderService.getOrders();

		List<OrderDetailsResponse>orderDetailsResponseList = new ArrayList<>();

		for(OrderDto orderDto:orderDtoList){
			OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
			orderDetailsResponse.setUserId(orderDto.getUserId());
			orderDetailsResponse.setItems(orderDto.getItems());
			orderDetailsResponse.setCost(orderDto.getCost());
			orderDetailsResponse.setStatus(orderDto.isStatus());
			orderDetailsResponse.setOrderId(orderDto.getOrderId());
			orderDetailsResponseList.add(orderDetailsResponse);
		}

		return orderDetailsResponseList;
	}
}

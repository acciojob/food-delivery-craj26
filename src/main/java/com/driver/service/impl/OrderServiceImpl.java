package com.driver.service.impl;

import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Override
    public OrderDto createOrder(OrderDto order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(order.getOrderId());
        orderEntity.setCost(order.getCost());
        orderEntity.setItems(order.getItems());
        orderEntity.setStatus(order.isStatus());
        orderEntity.setUserId(order.getUserId());

        orderRepository.save(orderEntity);

        return order;
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);

        OrderDto orderDto = new OrderDto();
        orderDto.setItems(orderEntity.getItems());
        orderDto.setStatus(orderEntity.isStatus());
        orderDto.setCost(orderEntity.getCost());
        orderDto.setOrderId(orderEntity.getOrderId());
        orderDto.setUserId(orderEntity.getUserId());
        return orderDto;
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {

        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        orderEntity.setUserId(order.getUserId());
        orderEntity.setStatus(order.isStatus());
        orderEntity.setCost(order.getCost());
        orderEntity.setItems(order.getItems());
        orderEntity.setOrderId(order.getOrderId());

        orderRepository.save(orderEntity);
        return order;
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        long id  = orderEntity.getId();
        orderRepository.deleteById(id);

    }

    @Override
    public List<OrderDto> getOrders() {
        List<OrderEntity>orderEntityList = (List<OrderEntity>) orderRepository.findAll();

        List<OrderDto>orderDtoList = new ArrayList<>();

        for(OrderEntity orderEntity:orderEntityList){

            OrderDto orderDto = new OrderDto();
            orderDto.setOrderId(orderEntity.getOrderId());
            orderDto.setItems(orderEntity.getItems());
            orderDto.setStatus(orderEntity.isStatus());
            orderDto.setCost(orderEntity.getCost());
            orderDto.setUserId(orderEntity.getUserId());

            orderDtoList.add(orderDto);

        }
        return orderDtoList;
    }
}

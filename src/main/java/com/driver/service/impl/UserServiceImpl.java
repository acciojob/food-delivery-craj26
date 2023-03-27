package com.driver.service.impl;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.UserResponse;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public  class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;


    @Override
    public UserDto createUser(UserDto user) throws Exception {
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(user.getUserId());
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        userRepository.save(userEntity);


        return user;
    }

    @Override
    public UserDto getUser(String email) throws Exception {
        UserEntity userEntity = userRepository.findByEmail(email);

        UserDto userDto = new UserDto();
        userDto.setLastName(userEntity.getLastName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setUserId(userEntity.getUserId());

        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        return null;
    }

    ////
    @Override
    public UserDto updateUser(String userId, UserDto user) throws Exception {
        UserEntity userEntity=userRepository.findByUserId(userId);
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        return user;
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        UserEntity userEntity=userRepository.findByUserId(userId);
        userRepository.delete(userEntity);
//        long id = userEntity.getId();
//        userRepository.deleteById(id);

    }

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity>userEntityList = (List<UserEntity>) userRepository.findAll();

        List<UserDto>userDtoList = new ArrayList<>();

        for(UserEntity userEntity:userEntityList){
            UserDto userDto = new UserDto();
            userDto.setFirstName(userEntity.getFirstName());
            userDto.setEmail(userEntity.getEmail());
            userDto.setLastName(userEntity.getLastName());
            userDto.setUserId(userEntity.getUserId());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
}

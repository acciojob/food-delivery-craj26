package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.UserResponse;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
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
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;


	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{

		UserEntity userEntity = userRepository.findById(Long.parseLong(id)).get();
		String email = userEntity.getEmail();

		UserDto userDto = userService.getUser(email);

		UserResponse userResponse = new UserResponse();
		userResponse.setLastName(userDto.getLastName());
		userResponse.setEmail(userDto.getEmail());
		userResponse.setFirstName(userDto.getFirstName());
		userResponse.setUserId(userDto.getUserId());

		return userResponse;
	}

	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto=new UserDto();
		userDto.setUserId(UUID.randomUUID().toString());
		userDto.setEmail(userDetails.getEmail());
		userDto.setFirstName(userDetails.getFirstName());
		userDto.setLastName(userDetails.getLastName());

		UserDto userDto1 = userService.createUser(userDto);

		//USER RESPONSE
		UserResponse userResponse = new UserResponse();
		userResponse.setEmail(userDto1.getEmail());
		userResponse.setUserId(userDto1.getUserId());
		userResponse.setFirstName(userDto1.getFirstName());
		userResponse.setLastName(userDto1.getLastName());

		return userResponse;
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto=new UserDto();
		userDto.setEmail(userDetails.getEmail());
		userDto.setLastName(userDetails.getLastName());
		userDto.setFirstName(userDetails.getFirstName());

		UserDto userDto1=userService.updateUser(id,userDto);

		//response Body
		UserResponse userResponse=new UserResponse();
		userResponse.setUserId(userDto1.getUserId());
		userResponse.setFirstName(userDto1.getFirstName());
		userResponse.setLastName(userDto1.getLastName());
		userResponse.setEmail(userDto1.getEmail());

		return  userResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{

		userService.deleteUser(id);
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("Delete");
		operationStatusModel.setOperationResult("successful");
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<UserResponse> getUsers(){
		List<UserDto> userDtoList=userService.getUsers();

		List<UserResponse>userResponseList=new ArrayList<>();
		for(UserDto userDto:userDtoList){
			UserResponse userResponse = new UserResponse();
			userResponse.setEmail(userDto.getEmail());
			userResponse.setFirstName(userDto.getFirstName());
			userResponse.setLastName(userDto.getLastName());
			userResponse.setUserId(userDto.getUserId());
			userResponseList.add(userResponse);
		}

		return userResponseList;
	}
	
}

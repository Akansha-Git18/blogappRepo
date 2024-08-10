package com.github.blog.services;

import java.util.List;

import com.github.blog.payloads.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto u);
	
	UserDto createUser(UserDto u);
	
	UserDto update(UserDto u,Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
}

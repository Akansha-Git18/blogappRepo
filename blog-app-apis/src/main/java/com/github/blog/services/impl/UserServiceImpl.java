package com.github.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.blog.config.AppConstants;
import com.github.blog.entities.Role;
import com.github.blog.entities.User;
import com.github.blog.exceptions.ResourceNotFoundException;
import com.github.blog.payloads.UserDto;
import com.github.blog.repostitories.RoleRepo;
import com.github.blog.repostitories.UserRepo;
import com.github.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRep;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User savedUser=this.userRep.save(user);
		return this.UserToDto(savedUser);
	}

	@Override
	public UserDto update(UserDto userDto, Integer userId) {
		User user=this.userRep.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", " Id ", userId));
			user.setName(userDto.getName());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());
			user.setAbout(userDto.getAbout());
			
			User updatedUser=this.userRep.save(user);
			UserDto userDto1=this.UserToDto(updatedUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user= this.userRep.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", " Id ", userId));
	
		return this.UserToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users=this.userRep.findAll();
		List<UserDto> userDtos=users.stream().map(user->this.UserToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user= this.userRep.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", " Id ", userId));
		this.userRep.delete(user);

	}
	
	private User dtoToUser(UserDto user) {
		User u=this.modelMapper.map(user, User.class);
//		u.setId(user.getId());
//		u.setName(user.getName());
//		u.setEmail(user.getEmail());
//		u.setAbout(user.getAbout());
//		u.setPassword(user.getPassword());
		
		return u;
	}
	
	private UserDto UserToDto(User user) {
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
	
		
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto u) {
		User user= this.dtoToUser(u);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User save= this.userRep.save(user);
		return this.UserToDto(save);
	}

}

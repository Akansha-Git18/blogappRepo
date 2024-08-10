package com.github.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.blog.entities.User;
import com.github.blog.exceptions.ResourceNotFoundException;
import com.github.blog.repostitories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	public UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", " username "+username, 0));
		
		return user;
	}

}

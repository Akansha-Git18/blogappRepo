package com.github.blog.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.github.blog.payloads.CategoryDto;

public interface CategoryService {

	//create
	 CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	 CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	//get
	 CategoryDto getCategory(Integer categoryId);
	
	//getall
	 List<CategoryDto> getCategories();
	
	//delete
	 void deleteCategory(Integer categoryId);
}

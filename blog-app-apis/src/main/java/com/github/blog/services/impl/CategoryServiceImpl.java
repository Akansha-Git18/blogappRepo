package com.github.blog.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.blog.entities.Category;
import com.github.blog.exceptions.ResourceNotFoundException;
import com.github.blog.payloads.CategoryDto;
import com.github.blog.repostitories.CategoryRepo;
import com.github.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category= this.dtoTocategory(categoryDto);
		Category savedcategory=this.categoryRepo.save(category);
		return this.categoryToDto(savedcategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", " Id ", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedcategory=this.categoryRepo.save(category);
		return this.categoryToDto(updatedcategory);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category= this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", " Id ", categoryId));
		return this.categoryToDto(category);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories=this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos=categories.stream().map((category)->this.categoryToDto(category)).collect(Collectors.toList());
		return categoryDtos;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", " Id ", categoryId));
		this.categoryRepo.delete(category);
	}
	
	private CategoryDto categoryToDto(Category category) {
		return this.modelMapper.map(category, CategoryDto.class);
	}
	
	private Category dtoTocategory(CategoryDto categoryDto) {
		return this.modelMapper.map(categoryDto, Category.class);
	}

}

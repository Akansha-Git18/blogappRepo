package com.github.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.blog.payloads.ApiResponse;
import com.github.blog.payloads.CategoryDto;
import com.github.blog.services.impl.CategoryServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryServiceImpl catService;
	
	//POST - create category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCatgeory(@Valid @RequestBody CategoryDto categotyDto){
		CategoryDto category=this.catService.createCategory(categotyDto);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
	}
	
	//PUT - update category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCatgeory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		CategoryDto category=this.catService.updateCategory(categoryDto,categoryId);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
	}
	
	//DELETE - delete category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCatgeory(@PathVariable Integer categoryId){
		this.catService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted successfully", true),HttpStatus.OK);
	}
	
	//GET - get category by Id
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCatgeory(@PathVariable Integer categoryId){
		CategoryDto category=this.catService.getCategory(categoryId);
		return ResponseEntity.ok(category);
	}
	
	//GET - get all category
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCatgeory(){
		return ResponseEntity.ok(this.catService.getCategories());
	}
}

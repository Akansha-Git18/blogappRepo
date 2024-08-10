package com.github.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.blog.entities.Post;
import com.github.blog.payloads.PostDto;
import com.github.blog.payloads.PostResponse;

@Service
public interface PostService {

	//create
	PostDto createPost(PostDto post,Integer userId,Integer categoryId);
	
	//update
	PostDto updatePost(PostDto post,Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//getPostById
	PostDto getPostById(Integer postId);
	
	//getAllPosts
	PostResponse getPosts(Integer pageNumber,Integer pagesize,String sortBy,String sortDir);
	
	//getPostByUsers
	List<PostDto> getPostByUser(Integer userId);
	
	//getPostByCategory
	List<PostDto> getPostByCatgeory(Integer categoryId);
	
	//searchPost
	List<PostDto> searchPosts(String keyword);
}

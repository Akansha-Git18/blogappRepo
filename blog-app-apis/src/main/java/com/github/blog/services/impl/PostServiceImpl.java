package com.github.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.blog.entities.Category;
import com.github.blog.entities.Post;
import com.github.blog.entities.User;
import com.github.blog.exceptions.ResourceNotFoundException;
import com.github.blog.payloads.PostDto;
import com.github.blog.payloads.PostResponse;
import com.github.blog.repostitories.CategoryRepo;
import com.github.blog.repostitories.PostRepo;
import com.github.blog.repostitories.UserRepo;
import com.github.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user= this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " userId ", userId));
		System.out.println(user.getName());
		Category category= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", " categoryId ", categoryId));
		
		Post post=this.postDtoToPost(postDto);
		post.setAddedDate(new Date());
		post.setImageName("default.png");
		post.setUser(user);
		post.setCategory(category);
		Post createdPost= this.postRepo.save(post);
		return this.postToDto(createdPost);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", " Post Id ", postId));
		
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		
		Post updatedUser=this.postRepo.save(post);
		return this.postToDto(updatedUser);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", " Post Id ", postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", " Post Id ", postId));
		
		return this.postToDto(post);
	}

	@Override
	public PostResponse getPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable page= PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Post> posts=this.postRepo.findAll(page);
		
		List<Post>post=posts.getContent();
		List<PostDto> postDtos= post.stream().map((p)-> this.postToDto(p)).collect(Collectors.toList());
		
		PostResponse response= new PostResponse();
		response.setContent(postDtos);
		response.setPageNumber(posts.getNumber());
		response.setPageSize(posts.getSize());
		response.setTotalPages(posts.getTotalPages());
		response.setTotalElements(posts.getTotalElements());
		response.setLastPage(posts.isLast());
		return response;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user= this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " UserId ", userId));
		List<Post> postsbyuserId=this.postRepo.findByUser(user);
		List<PostDto> postDtos=postsbyuserId.stream().map((post)-> this.postToDto(post)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByCatgeory(Integer categoryId) {
		Category category= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", " CategoryId ", categoryId));
		List<Post> postsbyCategoryId=this.postRepo.findByCategory(category);
		List<PostDto> postDtos=postsbyCategoryId.stream().map((post)-> this.postToDto(post)).collect(Collectors.toList());
		return postDtos;
	}

	
	public PostDto postToDto(Post post) {
		return this.modelMapper.map(post, PostDto.class);
	}
	
	public Post postDtoToPost(PostDto postDto) {
		return this.modelMapper.map(postDto, Post.class);
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> post=this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos=post.stream().map((p)-> this.postToDto(p)).collect(Collectors.toList());
		return postDtos;
	}
}

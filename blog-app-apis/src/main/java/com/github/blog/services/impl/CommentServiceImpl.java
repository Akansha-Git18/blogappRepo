package com.github.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.github.blog.entities.Comment;
import com.github.blog.entities.Post;
import com.github.blog.exceptions.ResourceNotFoundException;
import com.github.blog.payloads.CommentDto;
import com.github.blog.repostitories.CommentRepo;
import com.github.blog.repostitories.PostRepo;
import com.github.blog.services.CommentService;
@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepo postRepo;

	@Override
	public CommentDto createComment(CommentDto comment, Integer postId) {
		Post post= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", " postId ", postId));
		
		Comment com= this.modelMapper.map(comment, Comment.class);
		com.setPost(post);
		
		Comment savedComment= this.commentRepo.save(com);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", " commentId ", commentId));
		this.commentRepo.delete(comment);
	}

}

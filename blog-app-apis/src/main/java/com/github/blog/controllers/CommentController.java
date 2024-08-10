package com.github.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.blog.payloads.CommentDto;
import com.github.blog.services.impl.CommentServiceImpl;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentServiceImpl commentService;
	
	@PostMapping("/post/{postId}")
	public ResponseEntity<CommentDto> createComment(@PathVariable Integer postId,
			@RequestBody CommentDto comment){
		CommentDto com= this.commentService.createComment(comment, postId);
		
		return new ResponseEntity<CommentDto>(com, HttpStatus.CREATED);
	}
}

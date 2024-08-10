package com.github.blog.services;

import com.github.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto comment,Integer postId);
	
	void deleteComment(Integer commentId);
}

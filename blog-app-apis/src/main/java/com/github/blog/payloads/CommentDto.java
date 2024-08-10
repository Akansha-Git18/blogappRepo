package com.github.blog.payloads;

import com.github.blog.entities.Post;

import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

	private int commentId;

	private String content;


}

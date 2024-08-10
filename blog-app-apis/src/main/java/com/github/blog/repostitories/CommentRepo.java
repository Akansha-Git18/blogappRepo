package com.github.blog.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.blog.entities.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {

}

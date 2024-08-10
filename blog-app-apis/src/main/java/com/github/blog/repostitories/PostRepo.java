package com.github.blog.repostitories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.blog.entities.Category;
import com.github.blog.entities.Post;
import com.github.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String title);
}

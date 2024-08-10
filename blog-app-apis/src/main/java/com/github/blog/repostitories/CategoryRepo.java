package com.github.blog.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.blog.entities.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}

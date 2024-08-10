package com.github.blog.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role	,Integer> {

}

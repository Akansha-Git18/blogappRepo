package com.github.blog;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.blog.config.AppConstants;
import com.github.blog.entities.Role;
import com.github.blog.repostitories.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	
	@Bean
	public ModelMapper modelmapper() {
		return new ModelMapper();
	}


	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("abcd"));
		
		try {
			Role role= new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			Role role1= new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("NORMAL_USER");
			
			List<Role> roles=new ArrayList<>();
			roles.add(role);
			roles.add(role1);
		
			List<Role> result=this.roleRepo.saveAll(roles);
		
			result.forEach(r->{
				System.out.println(r.getName());
			}
					);
		}
		catch(Exception e) {
		e.printStackTrace();	
		}
	}
}

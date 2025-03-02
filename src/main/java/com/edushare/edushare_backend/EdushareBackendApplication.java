package com.edushare.edushare_backend;

import com.edushare.edushare_backend.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EdushareBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdushareBackendApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(UserService userService){
		return args -> {
			// Create and save a Professor

//			Professor professor = new Professor();
//			professor.setUsername("prof");
//			professor.setEmail("prof@gmail.com");
//			professor.setPassword("123");
//			professor.setRole(Role.PROFESSOR);
//			userService.registerUser(professor);
//
//			// Create and save a Student
//			Student student = new Student();
//			student.setUsername("std");
//			student.setEmail("std@gmail.com");
//			student.setPassword("123");
//			student.setRole(Role.STUDENT);
//			userService.registerUser(student);

		};
	}
}

package com.edushare.edushare_backend;

import com.edushare.edushare_backend.entities.Teacher;
import com.edushare.edushare_backend.entities.Student;
import com.edushare.edushare_backend.enums.Role;
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
			// Create and save a Teacher

//			Teacher teacher = new Teacher();
//			teacher.setUsername("prof");
//			teacher.setEmail("prof@gmail.com");
//			teacher.setPassword("123");
//			teacher.setRole(Role.TEACHER);
//			userService.registerUser(teacher);
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

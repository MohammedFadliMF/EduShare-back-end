package com.edushare.edushare_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edushare.edushare_backend.entities.Course;

public interface CourseRepository extends JpaRepository<Course,Long> {
    
}

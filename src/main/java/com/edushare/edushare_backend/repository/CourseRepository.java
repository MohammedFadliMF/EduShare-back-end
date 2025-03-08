package com.edushare.edushare_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edushare.edushare_backend.entities.Course;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Optional<Course> findByCourseId(Long courseId);
}

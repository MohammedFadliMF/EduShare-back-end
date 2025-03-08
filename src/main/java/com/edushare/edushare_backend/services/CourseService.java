package com.edushare.edushare_backend.services;

import com.edushare.edushare_backend.entities.Course;
import com.edushare.edushare_backend.repository.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }
}

package com.edushare.edushare_backend.web;

import com.edushare.edushare_backend.entities.Course;
import com.edushare.edushare_backend.exceptions.CourseException;
import com.edushare.edushare_backend.exceptions.StudentException;
import com.edushare.edushare_backend.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PreAuthorize("hasAuthority('SCOPE_STUDENT')")
    @PostMapping("/enrollInCourse/{courseId}")
    public ResponseEntity<Course> enrollInCourse(@RequestHeader("Authorization") String token, @PathVariable Long courseId) throws StudentException, CourseException {
        Course course =studentService.enrollInCourse(token,courseId);
        return ResponseEntity.ok(course);
    }
}

package com.edushare.edushare_backend.services;

import com.edushare.edushare_backend.entities.Course;
import com.edushare.edushare_backend.entities.Student;
import com.edushare.edushare_backend.exceptions.CourseException;
import com.edushare.edushare_backend.exceptions.StudentException;
import com.edushare.edushare_backend.repository.CourseRepository;
import com.edushare.edushare_backend.repository.StudentRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class StudentService {

    private final CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private JwtDecoder jwtDecoder;

    public StudentService(StudentRepository studentRepository, JwtDecoder jwtDecoder, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.jwtDecoder = jwtDecoder;
        this.courseRepository = courseRepository;
    }

    public Course enrollInCourse (String token, Long courseId) throws StudentException, CourseException {
        Jwt jwt=jwtDecoder.decode(token.substring(7));
        String email=jwt.getSubject().toString();

        Student student= studentRepository.findByEmail(email).orElseThrow(() -> new StudentException("Student not found"));
        Course course=courseRepository.findByCourseId(courseId).orElseThrow(() -> new CourseException("Course not found"));

        course.getEnrolledStudents().add(student);
        return courseRepository.save(course);
    }
//    public void TakeQuiz(String token , Long quizId , Map<Integer, Set<String>> answers) throws StudentException {
//        Jwt jwt=jwtDecoder.decode(token.substring(7));
//        String email=jwt.getSubject().toString();
//        Student student= studentRepository.findByEmail(email).orElseThrow(() -> new StudentException("Student not found"));
//    }
}

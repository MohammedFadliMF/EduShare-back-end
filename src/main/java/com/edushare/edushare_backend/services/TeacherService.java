package com.edushare.edushare_backend.services;

import com.edushare.edushare_backend.entities.Course;
import com.edushare.edushare_backend.entities.CourseContent;
import com.edushare.edushare_backend.entities.Professor;
import com.edushare.edushare_backend.repository.CourseRepository;
import com.edushare.edushare_backend.repository.ProfessorRepository;
import com.edushare.edushare_backend.repository.QuizRepository;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfessorService {
    private ProfessorRepository professorRepository;
    private CourseRepository courseRepository;
    private QuizRepository quizRepository;
    private JwtDecoder jwtDecoder;

    public ProfessorService(ProfessorRepository professorRepository, CourseRepository courseRepository, QuizRepository quizRepository, JwtDecoder jwtDecoder) {
        this.professorRepository = professorRepository;
        this.courseRepository = courseRepository;
        this.quizRepository = quizRepository;
        this.jwtDecoder = jwtDecoder;
    }

    public Course createCourse(String token, String title, String description) {
        String email=jwtDecoder.decode(token).getSubject();
        Professor professor=professorRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setProfessor(professor);
        return courseRepository.save(course);
    }

    public Course addContent(CourseContent courseContent,Long CourseId){
        Optional<Course> course = courseRepository.findById(CourseId);
        if(course.isPresent()){
            course.get().getContents().add(courseContent);
            return courseRepository.save(course.get());
        }
        throw new RuntimeException("Course not found");
    }
    public void deleteContent(){}
    public void updateContent(){}
    public void createQuizzes(){}
}

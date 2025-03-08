package com.edushare.edushare_backend.services;

import com.edushare.edushare_backend.entities.*;
import com.edushare.edushare_backend.enums.FileType;
import com.edushare.edushare_backend.exceptions.CourseException;
import com.edushare.edushare_backend.exceptions.TeacherException;
import com.edushare.edushare_backend.repository.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class TeacherService {
    private final QuestionRepository questionRepository;
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private CourseRepository courseRepository;
    private QuizRepository quizRepository;
    private JwtDecoder jwtDecoder;

    public TeacherService(TeacherRepository teacherRepository, CourseRepository courseRepository, QuizRepository quizRepository, JwtDecoder jwtDecoder, StudentRepository studentRepository, QuestionRepository questionRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.quizRepository = quizRepository;
        this.jwtDecoder = jwtDecoder;
        this.studentRepository = studentRepository;
        this.questionRepository = questionRepository;
    }

    public Course createCourse(String token, String title, String description) throws TeacherException {
        Jwt jwt=jwtDecoder.decode(token.substring(7));
        String email=jwt.getSubject().toString();
        Teacher teacher= teacherRepository.findByEmail(email).orElseThrow(() -> new TeacherException("Teacher not found"));

        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setTeacher(teacher);
        return courseRepository.save(course);
    }

    public Course addContent(CourseContent courseContent,Long CourseId) throws CourseException {
        Optional<Course> course = courseRepository.findById(CourseId);
        if(course.isPresent()){
            course.get().getContents().add(courseContent);
            return courseRepository.save(course.get());
        }
        throw new CourseException("Course not found");
    }
    public FileType mapContentTypeToFileType(String contentType) {
        switch (contentType) {
            case "application/pdf":
                return FileType.PDF;
            case "application/msword":
                return FileType.DOC;
            case "application/vnd.ms-powerpoint":
                return FileType.PPT;
            case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
                return FileType.PPTX;
            case "text/plain":
                return FileType.TXT;
            case "video/mp4":
                return FileType.MP4;
            case "application/zip":
                return FileType.ZIP;
            case "application/x-rar-compressed":
                return FileType.RAR;
            default:
                throw new IllegalArgumentException("Unsupported file type: " + contentType);
        }
    }

    public void deleteContent(){}
    public void updateContent(){}

    public Course createQuiz(String token, String quizName, Set<Question> questions,Long courseId) throws CourseException {
        Quiz quiz = new Quiz();
        quiz.setQuizName(quizName);
        quiz.setQuestions(questions);

        Course course=courseRepository.findByCourseId(courseId).orElseThrow(() -> new CourseException("Course not found"));
        course.getQuizzes().add(quiz);

        return courseRepository.save(course);
    }
}

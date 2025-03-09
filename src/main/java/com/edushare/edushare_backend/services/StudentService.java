package com.edushare.edushare_backend.services;

import com.edushare.edushare_backend.entities.*;
import com.edushare.edushare_backend.exceptions.CourseException;
import com.edushare.edushare_backend.exceptions.QuizException;
import com.edushare.edushare_backend.exceptions.StudentException;
import com.edushare.edushare_backend.repository.CourseRepository;
import com.edushare.edushare_backend.repository.QuizAttemptRepository;
import com.edushare.edushare_backend.repository.QuizRepository;
import com.edushare.edushare_backend.repository.StudentRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class StudentService {

    private final CourseRepository courseRepository;
    private final QuizRepository quizRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private StudentRepository studentRepository;
    private JwtDecoder jwtDecoder;

    public StudentService(StudentRepository studentRepository, JwtDecoder jwtDecoder, CourseRepository courseRepository, QuizRepository quizRepository, QuizAttemptRepository quizAttemptRepository) {
        this.studentRepository = studentRepository;
        this.jwtDecoder = jwtDecoder;
        this.courseRepository = courseRepository;
        this.quizRepository = quizRepository;
        this.quizAttemptRepository = quizAttemptRepository;
    }

    public Course enrollInCourse (String token, Long courseId) throws StudentException, CourseException {
        Jwt jwt=jwtDecoder.decode(token.substring(7));
        String email=jwt.getSubject().toString();

        Student student= studentRepository.findByEmail(email).orElseThrow(() -> new StudentException("Student not found"));
        Course course=courseRepository.findByCourseId(courseId).orElseThrow(() -> new CourseException("Course not found"));

        course.getEnrolledStudents().add(student);
        return courseRepository.save(course);
    }

    public QuizAttempt TakeQuiz(String token,Long quizId,Set<StudentAnswer> answers) throws StudentException, QuizException {
        Jwt jwt=jwtDecoder.decode(token.substring(7));
        String email=jwt.getSubject().toString();

        Student student= studentRepository.findByEmail(email).orElseThrow(() -> new StudentException("Student not found"));
        Quiz quiz=quizRepository.findByQuizId(quizId).orElseThrow(()-> new QuizException("Quiz not found"));

        int correctCount = 0;

        for (StudentAnswer answer : answers) {
            if (!answer.getQuestion().getChoices().contains(answer.getSelectedAnswer())){
                throw new QuizException("Invalid answer: Selected answer is not in the available choices.");
            }
            if (answer.getQuestion().getCorrectAnswer().equals(answer.getSelectedAnswer())) {
                correctCount++;
            }
        }

        double score = (double) correctCount / quiz.getQuestions().size() * 100;

        QuizAttempt quizAttempt=new QuizAttempt();
        quizAttempt.setQuiz(quiz);
        quizAttempt.setAnswers(answers);
        quizAttempt.setScore(score);
        quizAttempt.setAttemptDate(LocalDateTime.now());
        student.getQuizAttempts().add(quizAttempt);

        return quizAttemptRepository.save(quizAttempt);
    }
}

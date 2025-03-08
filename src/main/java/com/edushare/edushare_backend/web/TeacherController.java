package com.edushare.edushare_backend.web;

import com.edushare.edushare_backend.entities.Course;
import com.edushare.edushare_backend.entities.CourseContent;
import com.edushare.edushare_backend.entities.Quiz;
import com.edushare.edushare_backend.entities.Student;
import com.edushare.edushare_backend.enums.FileType;
import com.edushare.edushare_backend.exceptions.CourseException;
import com.edushare.edushare_backend.exceptions.TeacherException;
import com.edushare.edushare_backend.services.TeacherService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/teacher")
@AllArgsConstructor
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    
    @PreAuthorize("hasAuthority('SCOPE_TEACHER')")
    @GetMapping("/roles")
    public ResponseEntity<String> getRoles(){
        return new ResponseEntity<String>("SCOPE_TEACHER",HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SCOPE_TEACHER')")
    @PostMapping("/create-course")
    public ResponseEntity<Course> createCourse(@RequestHeader("Authorization") String token, @RequestBody Course course) throws TeacherException {
        Course course1 = teacherService.createCourse(token,course.getTitle(),course.getDescription());
        return ResponseEntity.ok(course1);
    }
    @PreAuthorize("hasAuthority('SCOPE_TEACHER')")
    @PostMapping(value = "/add-content/{courseId}",consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Course> addCourse(@RequestHeader("Authorization") String token, @RequestParam("file") MultipartFile file,@PathVariable Long courseId) throws CourseException, IOException {
        CourseContent courseContent = new CourseContent();
        courseContent.setFileName(file.getOriginalFilename());

        // Map Content-Type to FileType
        FileType fileType = teacherService.mapContentTypeToFileType(file.getContentType());

        courseContent.setFileType(fileType);
        courseContent.setFileContent(file.getBytes());
        Course updatedCourse =teacherService.addContent(courseContent,courseId);
        return ResponseEntity.ok(updatedCourse);
    }
    @PreAuthorize("hasAuthority('SCOPE_TEACHER')")
    @PostMapping("/create-quiz/{courseId}")
    public ResponseEntity<Course> createQuiz(@RequestHeader("Authorization") String token, @RequestBody Quiz quiz, @PathVariable Long courseId) throws CourseException {
        Course course =teacherService.createQuiz(token,quiz.getQuizName(),quiz.getQuestions(),courseId);
        return ResponseEntity.ok(course);
    }

}

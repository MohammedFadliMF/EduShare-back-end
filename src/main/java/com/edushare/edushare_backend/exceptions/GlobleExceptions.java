package com.edushare.edushare_backend.exceptions;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobleExceptions {

    @ExceptionHandler(CourseException.class)
    public ResponseEntity<ErrorDetails> CourseExceptionHandler(CourseException ue, WebRequest req){
        ErrorDetails error=new ErrorDetails(ue.getMessage(),req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TeacherException.class)
    public ResponseEntity<ErrorDetails> TeacherExceptionHandler(TeacherException ue, WebRequest req){
        ErrorDetails error=new ErrorDetails(ue.getMessage(),req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<ErrorDetails> StudentExceptionHandler(StudentException ue, WebRequest req){
        ErrorDetails error=new ErrorDetails(ue.getMessage(),req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException me) {

        ErrorDetails error = new ErrorDetails(me.getBindingResult().getFieldError().getDefaultMessage(),"validation error", LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

}

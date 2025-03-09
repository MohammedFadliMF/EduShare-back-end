package com.edushare.edushare_backend.repository;

import com.edushare.edushare_backend.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Optional<Quiz> findByQuizId(Long quizId);
}

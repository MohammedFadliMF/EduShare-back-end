package com.edushare.edushare_backend.repository;

import com.edushare.edushare_backend.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}

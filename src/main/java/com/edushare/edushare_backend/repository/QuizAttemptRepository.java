package com.edushare.edushare_backend.repository;

import com.edushare.edushare_backend.entities.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

}

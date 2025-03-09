package com.edushare.edushare_backend.repository;

import com.edushare.edushare_backend.entities.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {
}

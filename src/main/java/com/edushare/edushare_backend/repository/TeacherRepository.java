package com.edushare.edushare_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edushare.edushare_backend.entities.Teacher;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher,Long>{
     Optional<Teacher> findByEmail(String email);
}

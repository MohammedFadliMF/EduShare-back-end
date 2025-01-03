package com.edushare.edushare_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edushare.edushare_backend.entities.Student;

public interface StudentRepository extends JpaRepository<Student,Long>{
    
}

package com.edushare.edushare_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edushare.edushare_backend.entities.Professor;

public interface ProfessorRepository extends JpaRepository<Professor,Long>{
    
}

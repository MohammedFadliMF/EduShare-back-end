package com.edushare.edushare_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edushare.edushare_backend.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{
    
}

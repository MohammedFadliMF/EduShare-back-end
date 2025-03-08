package com.edushare.edushare_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edushare.edushare_backend.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}

package com.codependentvariables.cab302groupproject.repository;

import com.codependentvariables.cab302groupproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
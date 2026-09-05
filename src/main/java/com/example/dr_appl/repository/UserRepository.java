package com.example.dr_appl.repository;

import com.example.dr_appl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    long countByRole(String role);
}

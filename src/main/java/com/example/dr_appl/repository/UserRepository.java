package com.example.dr_appl.repository;

import com.example.dr_appl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // This helps Spring Security find users by their email during login
    Optional<User> findByEmail(String email);
}

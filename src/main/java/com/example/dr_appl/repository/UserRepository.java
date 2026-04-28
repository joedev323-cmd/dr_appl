package com.example.dr_appl.repository;

import com.example.dr_appl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); 
    long countByRole(String role);
}

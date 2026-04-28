package com.example.dr_appl.model;

import com.example.dr_appl.model.entity.Doctor;
import com.example.dr_appl.model.entity.Patient;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data // This generates getters, setters, and toString automatically if you use Lombok
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String role; // Default role for new signups

    // mappedBy refers to the "user" field in the Doctor/Patient classes
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Doctor doctor;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Patient patient;
    
    public User() {
    }
    public User(String firstName, String lastName, String email, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
 public String getEmail() {
    return email;
}
public String getPassword() {
    return password;
}
public String getRole() {
    return role;
}
    
}

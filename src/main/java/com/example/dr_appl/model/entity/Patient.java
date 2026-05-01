package com.example.dr_appl.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.dr_appl.model.User;
import com.example.dr_appl.model.enums.Gender;
 

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Consistent with other entities
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String fullname;
    
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private LocalDate Dob;
    
    @Column(columnDefinition = "TEXT")  
    private String medicalHistory;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String insuranceProvider;
  
    public Patient() {}
    
    public Patient(Long id, User user, String fullname, LocalDate dob, String medicalHistory, Gender gender,
        String insuranceProvider) {
      this.id = id;
      this.user = user;
      this.fullname = fullname;
      this.Dob = dob;
      this.medicalHistory = medicalHistory;
      this.gender = gender;
      this.insuranceProvider = insuranceProvider;
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public User getUser() {
      return user;
    }

    public void setUser(User user) {
      this.user = user;
    }

    public String getFullname() {
      return fullname;
    }

    public void setFullname(String fullname) {
      this.fullname = fullname;
    }

    public LocalDate getdob() {
      return Dob;
    }

    public void setdob(LocalDate dob) {
      this.Dob = dob;
    }

    public String getMedicalHistory() {
      return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
      this.medicalHistory = medicalHistory;
    }

    public Gender getGender() {
      return gender;
    }

    public void setGender(Gender gender) {
      this.gender = gender;
    }

    public String getInsuranceProvider() {
      return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
      this.insuranceProvider = insuranceProvider;
    }
    
    
    
  }
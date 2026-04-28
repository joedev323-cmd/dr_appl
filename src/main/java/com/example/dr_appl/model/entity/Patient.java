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

    private String name;
    private LocalDate dob;
    
    @Column(columnDefinition = "TEXT")  
    private String medicalHistory;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String insuranceProvider;

    
    
    public Patient() {}
    
    public Patient(Long id, String name, LocalDate dob, String medicalHistory, Gender gender, String insuranceProvider,
        User user) {
      this.id = id;
      this.name = name;
      this.dob = dob;
      this.medicalHistory = medicalHistory;
      this.gender = gender;
      this.insuranceProvider = insuranceProvider;
      this.user = user;
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public LocalDate getDob() {
      return dob;
    }

    public void setDob(LocalDate dob) {
      this.dob = dob;
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

    public User getUser() {
      return user;
    }

    public void setUser(User user) {
      this.user = user;
    }
    
    
  }
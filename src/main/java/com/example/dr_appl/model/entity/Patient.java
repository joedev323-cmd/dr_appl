package com.example.dr_appl.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
 

import com.example.dr_appl.model.enums.Gender;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Consistent with other entities

    private String name;
    private LocalDate dob;
    
    @Column(columnDefinition = "TEXT")  
    private String medicalHistory;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String insuranceProvider;

    
    public Patient() {}
    
    public Patient(String name, LocalDate dob, String medicalHistory, Gender gender, String insuranceProvider) {
      this.name = name;
      this.dob = dob;
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
    
 }
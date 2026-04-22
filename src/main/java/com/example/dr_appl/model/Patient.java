package com.example.dr_appl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    private String patientName;
    private LocalDateTime appointmentTime;
    private LocalDate dob;
    private String medicalHistory;
    private String gender;
    private String insuranceDetails;

    public Patient() {}

    public Patient(String patientName, LocalDateTime appointmentTime,LocalDate dob, String medicalHistory,String gender, String insuranceDetails) {
        this.patientName = patientName;
        this.appointmentTime = appointmentTime;
        this.dob = dob;
        this.medicalHistory = medicalHistory;
        this.gender = gender;
        this.insuranceDetails = insuranceDetails;
    }
    public Long getPatientId() {
    return patientId;
}

  public String getPatientName() {
      return patientName;
  }

  public LocalDate getDob() {
    return dob;
  }

  public LocalDateTime getAppointmentTime() {
    return appointmentTime;
  }

  public String getMedicalHistory() {
    return medicalHistory;
  }

  public String getGender() {
    return gender;
  }

  public String getInsuranceDetails() {
    return insuranceDetails;
  }   

}
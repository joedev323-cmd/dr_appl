package com.example.dr_appl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients_table")
public class patientmodel {
  @Id

  private String patient_id;
  private String patient_name;
  private String Appointment_time;
  private String DOB;
  

  
    
}

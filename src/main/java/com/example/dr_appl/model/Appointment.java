package com.example.dr_appl.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AppointmentId;
    
    private LocalDate Date;
    @Enumerated(EnumType.STRING)
    private appntStatus appntStatus;

   public Appointment(){}

   public Appointment(LocalDate Date,appntStatus appntStatus){
    this.Date = Date;
    this.appntStatus = appntStatus;
   }
   public Long getAppointmentId() {
        return AppointmentId;
    }
     public LocalDate getDate() {
        return Date;
    }
     public appntStatus getAppntStatus() {
        return appntStatus;
    }
}

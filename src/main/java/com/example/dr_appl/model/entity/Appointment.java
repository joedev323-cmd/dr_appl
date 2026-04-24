package com.example.dr_appl.model.entity;

import com.example.dr_appl.model.enums.*;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Index;

@Entity

@Table(indexes = {
    @Index(name = "idx_room_time", columnList = "room_id, startTime, endTime")
})
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Room room;

   
   public Appointment(LocalDateTime startTime, LocalDateTime endTime, AppointmentStatus status, Doctor doctor,
            Patient patient, Room room) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.doctor = doctor;
        this.patient = patient;
        this.room = room;
    }


   public Appointment(){}


   public Long getId() {
    return id;
   }


   public void setId(Long id) {
    this.id = id;
   }


   public LocalDateTime getStartTime() {
    return startTime;
   }


   public void setStartTime(LocalDateTime startTime) {
    if (this.endTime != null && startTime.isAfter(this.endTime)) {
        throw new IllegalArgumentException("Start time must be before end time");
    }
    this.startTime = startTime;
   }


   public LocalDateTime getEndTime() {
    return endTime;
   }


   public void setEndTime(LocalDateTime endTime) {
    if (this.startTime != null && endTime.isBefore(this.startTime)) {
        throw new IllegalArgumentException("wee mzee rada unamalizaje ka ujaanza");
    }
    this.endTime = endTime;
   }


   public AppointmentStatus getStatus() {
    return status;
   }


   public void setStatus(AppointmentStatus status) {
    this.status = status;
   }


   public Doctor getDoctor() {
    return doctor;
   }


   public void setDoctor(Doctor doctor) {
    this.doctor = doctor;
   }


   public Patient getPatient() {
    return patient;
   }


   public void setPatient(Patient patient) {
    this.patient = patient;
   }


   public Room getRoom() {
    return room;
   }


   public void setRoom(Room room) {
    this.room = room;
   }

   
}

 package com.example.dr_appl.repository;

import com.example.dr_appl.model.entity.Appointment;
import com.example.dr_appl.model.entity.Doctor;
import com.example.dr_appl.model.entity.Patient;
import com.example.dr_appl.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // 1. Fix for your controller: Fetches unique patients for a specific doctor
    @Query("SELECT DISTINCT a.patient FROM Appointment a WHERE a.doctor = :doctor")
 
    List<Patient> findDistinctPatientsByDoctor(@Param("doctor") Doctor doctor);


    // 2. Your room occupancy check
    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.room = :room " +
           "AND (:start < a.endTime AND :end > a.startTime)")
    boolean isRoomOccupied(@Param("room") Room room, 
                           @Param("start") LocalDateTime start, 
                           @Param("end") LocalDateTime end);
}

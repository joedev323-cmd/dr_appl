 package com.example.dr_appl.repository;

import com.example.dr_appl.model.entity.Appointment;
import com.example.dr_appl.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
  
    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.room = :room " +
           "AND (:start < a.endTime AND :end > a.startTime)")
    boolean isRoomOccupied(@Param("room") Room room, 
                           @Param("start") LocalDateTime start, 
                           @Param("end") LocalDateTime end);
}

package com.example.dr_appl.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.dr_appl.model.entity.Availability;
import com.example.dr_appl.model.entity.Doctor;
import com.example.dr_appl.model.entity.Room;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    
    List<Availability> findByDoctorOrderByStartTimeAsc(Doctor doctor);
    List<Availability> findByRoomOrderByStartTimeAsc(Room room);

    // This matches the call: availabilityRepository.isRoomOccupied(selectedRoom, rangeStart, rangeEnd)
    @Query("SELECT COUNT(a) > 0 FROM Availability a WHERE a.room = :room " +
           "AND (:start < a.endTime AND :end > a.startTime)")
    boolean isRoomOccupied(@Param("room") Room room, 
                           @Param("start") LocalDateTime start, 
                           @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(a) > 0 FROM Availability a WHERE a.doctor = :doctor " +
           "AND (:start < a.endTime AND :end > a.startTime)")
    boolean isDoctorBusy(@Param("doctor") Doctor doctor, 
                         @Param("start") LocalDateTime start, 
                         @Param("end") LocalDateTime end);
}

package com.example.dr_appl.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.dr_appl.model.entity.Availability;
import com.example.dr_appl.model.entity.Doctor;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    @Query("SELECT COUNT(a) FROM Availability a WHERE a.room.id = :roomId " +
           "AND (:start < a.endTime AND :end > a.startTime)")
    Long countRoomConflicts(@Param("roomId") Long roomId, 
                            @Param("start") LocalDateTime start, 
                            @Param("end") LocalDateTime end);
                            List<Availability> findByDoctorOrderByStartTimeAsc(Doctor doctor);
}

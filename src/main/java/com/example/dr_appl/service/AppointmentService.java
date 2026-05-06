 package com.example.dr_appl.service;

import com.example.dr_appl.model.entity.*;
import com.example.dr_appl.model.enums.*;
import com.example.dr_appl.model.dto.AppointmentDTO;
import com.example.dr_appl.repository.*;
import com.example.dr_appl.exception.ResourceLockedException;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final DoctorRepository doctorRepo;
    private final RoomRepository roomRepo;

    public AppointmentService(AppointmentRepository appointmentRepo, 
                              DoctorRepository doctorRepo, 
                              RoomRepository roomRepo) {
        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
        this.roomRepo = roomRepo;
    }

    @Transactional
    public void secureBooking(AppointmentDTO dto) {
        try {
            Doctor doctor = doctorRepo.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

            // Simple logic to find a room; adjust based on your Room entity logic
            Room room = roomRepo.findAll().stream()
                .filter(r -> r.getRoomStatus() == RoomStatus.AVAILABLE)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No rooms available"));

            LocalDateTime startTime = dto.getAppointmentDate();
            LocalDateTime endTime = startTime.plusMinutes(30);

            if (appointmentRepo.isRoomOccupied(room, startTime, endTime)) {
                throw new ResourceLockedException("This slot is already taken.");
            }

            Appointment appt = new Appointment();
            appt.setDoctor(doctor);
            appt.setRoom(room);
            appt.setStartTime(startTime);
            appt.setEndTime(endTime);
            appt.setStatus(AppointmentStatus.SCHEDULED);

            appointmentRepo.save(appt);

        } catch (ObjectOptimisticLockingFailureException e) {
            throw new ResourceLockedException("Concurrent booking detected. Try again.");
        }
    }

    public List<LocalTime> generateAvailableSlots(LocalDate date, Room room) {
        List<LocalTime> allSlots = new ArrayList<>();
        LocalTime time = LocalTime.of(10, 0);

        while (!time.isAfter(LocalTime.of(14, 30))) {
            boolean occupied = (room == null) ? false : 
                appointmentRepo.isRoomOccupied(room, date.atTime(time), date.atTime(time).plusMinutes(30));
            
            if (!occupied) { allSlots.add(time); }
            time = time.plusMinutes(30);
        } 
        return allSlots;
    }

    public long countAll() {
        return appointmentRepo.count();
    }
    // Inside AppointmentService.java

public List<Appointment> findAll() {
    return appointmentRepo.findAll();
}
   
}
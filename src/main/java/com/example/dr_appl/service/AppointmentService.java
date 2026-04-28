package com.example.dr_appl.service;

import com.example.dr_appl.model.entity.*;
import com.example.dr_appl.model.enums.*;
import com.example.dr_appl.repository.*;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final DoctorRepository doctorRepo;
    private final RoomRepository roomRepo;
    
    // Constructor Injection: The system provides the repositories automatically
    public AppointmentService(AppointmentRepository appointmentRepo, 
                              DoctorRepository doctorRepo, 
                              RoomRepository roomRepo) {
        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
        this.roomRepo = roomRepo;
    }
    
   @Transactional
    public void createAppointment(Appointment appointment) {
    try {

        boolean occupied = appointmentRepo.isRoomOccupied(
            appointment.getRoom(),
            appointment.getStartTime(),
            appointment.getEndTime()
        );

        if (occupied) {
            throw new IllegalStateException("Room already booked");
        }

        appointmentRepo.save(appointment);

    } catch (ObjectOptimisticLockingFailureException e) {
        throw new IllegalStateException("Concurrent booking detected. Try again.");
        }   
    }
    
   public List<java.time.LocalTime> generateAvailableSlots(java.time.LocalDate date, Room room) {
    List<java.time.LocalTime> allSlots = new java.util.ArrayList<>();
    java.time.LocalTime time = java.time.LocalTime.of(10, 0);

    while (!time.isAfter(java.time.LocalTime.of(14, 30))) {
        // This is the core 'Critical Section' check
        boolean occupied = (room == null) ? false : appointmentRepo.isRoomOccupied(room, 
                                date.atTime(time), 
                                date.atTime(time).plusMinutes(30));
        
        if (!occupied) {
            allSlots.add(time);
        }
        time = time.plusMinutes(30);
    } 
    return allSlots;
}


   public Object countAll() {
 
    throw new UnsupportedOperationException("Unimplemented method 'countAll'");
   }

    
}

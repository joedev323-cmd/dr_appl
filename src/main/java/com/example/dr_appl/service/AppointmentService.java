package com.example.dr_appl.service;

import com.example.dr_appl.model.entity.*;
import com.example.dr_appl.model.enums.*;
import com.example.dr_appl.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 

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
        // 1. Check if the Room (Critical Section) is FREE
        if (appointment.getRoom().getRoomStatus() == RoomStatus.OCCUPIED) {
            throw new IllegalStateException("Room is already occupied!");
        }

        // 2. Check if the Doctor (Processor) is BUSY
        if (appointment.getDoctor().getStatus() == DoctorStatus.BUSY) {
            throw new IllegalStateException("Doctor is currently busy!");
        }

        // 3. Perform the "Lock" (Update statuses)
        appointment.getRoom().setRoomStatus(RoomStatus.OCCUPIED);
        appointment.getDoctor().setStatus(DoctorStatus.BUSY);

        // 4. Save everything (The Atomic Commit)
        appointmentRepo.save(appointment);
        roomRepo.save(appointment.getRoom());
        doctorRepo.save(appointment.getDoctor());
    }
}

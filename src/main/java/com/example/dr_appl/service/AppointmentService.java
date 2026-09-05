package com.example.dr_appl.service;

import com.example.dr_appl.model.dto.AppointmentDTO;
import com.example.dr_appl.model.entity.*;
import com.example.dr_appl.model.enums.*;
import com.example.dr_appl.repository.*;
import com.example.dr_appl.exception.ResourceLockedException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final DoctorRepository doctorRepo;
    private final RoomRepository roomRepo;
    private final PatientRepository patientRepo;

    public AppointmentService(
            AppointmentRepository appointmentRepo,
            DoctorRepository doctorRepo,
            RoomRepository roomRepo,
            PatientRepository patientRepo) {

        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
        this.roomRepo = roomRepo;
        this.patientRepo = patientRepo;
    }

    @Transactional
    public void secureBooking(AppointmentDTO dto) {

        try {
            // -----------------------------------------
            // 1. Get the currently logged-in user
            // -----------------------------------------
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null ||
                    !authentication.isAuthenticated()) {

                throw new IllegalStateException("User is not authenticated.");
            }

            String email = authentication.getName();

            // -----------------------------------------
            // 2. Find the patient using their email
            // -----------------------------------------
            Patient patient = patientRepo.findByUserEmail(email)
                    .orElseThrow(() -> new IllegalStateException(
                            "Patient profile not found for: " + email));

            // -----------------------------------------
            // 3. Find selected doctor
            // -----------------------------------------
            if (dto.getDoctorId() == null) {
                throw new IllegalArgumentException(
                        "Please select a doctor.");
            }

            Doctor doctor = doctorRepo.findById(dto.getDoctorId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Doctor not found."));

            // -----------------------------------------
            // 4. Validate appointment date
            // -----------------------------------------
            if (dto.getAppointmentDate() == null) {
                throw new IllegalArgumentException(
                        "Please select an appointment date and time.");
            }

            LocalDateTime startTime = dto.getAppointmentDate();
            LocalDateTime endTime = startTime.plusMinutes(30);

            // -----------------------------------------
            // 5. Find an available room
            // -----------------------------------------
            Room room = roomRepo.findAll()
                    .stream()
                    .filter(r -> r.getRoomStatus() == RoomStatus.AVAILABLE)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "No rooms are currently available."));

            // -----------------------------------------
            // 6. Check whether room is already occupied
            // -----------------------------------------
            if (appointmentRepo.isRoomOccupied(
                    room,
                    startTime,
                    endTime)) {

                throw new ResourceLockedException(
                        "This appointment slot is already taken.");
            }

            // -----------------------------------------
            // 7. Create appointment
            // -----------------------------------------
            Appointment appt = new Appointment();

            appt.setPatient(patient); // IMPORTANT
            appt.setDoctor(doctor);
            appt.setRoom(room);
            appt.setStartTime(startTime);
            appt.setEndTime(endTime);
            appt.setStatus(AppointmentStatus.SCHEDULED);

            // -----------------------------------------
            // 8. Save appointment
            // -----------------------------------------
            appointmentRepo.save(appt);

        } catch (ObjectOptimisticLockingFailureException e) {

            throw new ResourceLockedException(
                    "Concurrent booking detected. Please try again.");
        }
    }

    public List<LocalTime> generateAvailableSlots(
            LocalDate date,
            Room room) {

        List<LocalTime> allSlots = new ArrayList<>();

        LocalTime time = LocalTime.of(10, 0);

        while (!time.isAfter(LocalTime.of(14, 30))) {

            boolean occupied = room != null &&
                    appointmentRepo.isRoomOccupied(
                            room,
                            date.atTime(time),
                            date.atTime(time).plusMinutes(30));

            if (!occupied) {
                allSlots.add(time);
            }

            time = time.plusMinutes(30);
        }

        return allSlots;
    }

    public long countAll() {
        return appointmentRepo.count();
    }

    public List<Appointment> findAll() {
        return appointmentRepo.findAll();
    }
}
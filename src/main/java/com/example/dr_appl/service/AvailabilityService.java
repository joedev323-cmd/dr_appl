package com.example.dr_appl.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dr_appl.model.entity.Availability;
import com.example.dr_appl.model.entity.Doctor;
import com.example.dr_appl.model.entity.Room;
import com.example.dr_appl.model.enums.AdminControlStatus;
import com.example.dr_appl.model.enums.DoctorIntent;
import com.example.dr_appl.repository.AvailabilityRepository;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    public String createSlot(Doctor doctor, Room room, LocalDateTime start, LocalDateTime end) {
        
        // 1. ADMIN CHECK: Is the doctor allowed in the system?
        if (doctor.getAdminStatus() == AdminControlStatus.SUSPENDED) {
            return "Cannot create slot: Doctor's system access is revoked.";
        }

        // 2. DOCTOR INTENT CHECK: Is the doctor currently on leave?
        if (doctor.getDoctorIntent() == DoctorIntent.ON_LEAVE) {
            return "Cannot create slot: Doctor is currently marked as 'On Leave'.";
        }

        // 3. OVERLAP CHECK: Use your existing repo methods
        if (availabilityRepository.isRoomOccupied(room, start, end)) {
            return "Room is already booked for this time.";
        }

        if (availabilityRepository.isDoctorBusy(doctor, start, end)) {
            return "Doctor already has a commitment at this time.";
        }

        // 4. SAVE
        Availability slot = new Availability(doctor, room, start, end);
        availabilityRepository.save(slot);
        return "Slot created successfully!";
    }
}
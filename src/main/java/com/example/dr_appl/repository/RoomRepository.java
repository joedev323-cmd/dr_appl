package com.example.dr_appl.repository;

import com.example.dr_appl.model.entity.Room;
import com.example.dr_appl.model.enums.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
     List<Room> findByRoomStatus(RoomStatus status);
}
 
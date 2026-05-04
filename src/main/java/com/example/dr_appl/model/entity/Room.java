package com.example.dr_appl.model.entity;

import com.example.dr_appl.model.enums.RoomStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

 @Entity
@Table(name = "rooms")  
public class Room {  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;  
    
    private String roomName;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
    
    @Version
    private Integer version;  
    
    
    
    public Room() {}
    
    public Room(Long roomId, String roomName, RoomStatus roomStatus, Integer version) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomStatus = roomStatus;
        this.version = version;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    
    public Room(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Long getRoomId() {
        return roomId;
    }


    public void setRoomid(Long roomid) {
        roomId = roomid;
    }


    public RoomStatus getRoomStatus() {
        return roomStatus;
    }


    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }
    
}

package com.example.dr_appl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Room_id;
    
    
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
    
    //foreign keys in jpa
    public Room(){}
    
    public void room( RoomStatus roomStatus){
        this.roomStatus = roomStatus;
    }
    
    public Long getRoom_id() {
        return Room_id;
    }
     
    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

}

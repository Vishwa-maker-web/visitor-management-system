package com.example.visitor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class MeetingRoom {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomName;
    private String status;

}

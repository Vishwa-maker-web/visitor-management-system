package com.example.visitor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ReceptionQueue {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
private String email;
private String name;
private String purpose;
private String status;
}

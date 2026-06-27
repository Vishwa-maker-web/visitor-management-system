package com.example.visitor.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "visitor")
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private String name;
    private String email;
    private String phone;
    private String purpose;
//    private String qrCode;
    private LocalDateTime passExpiry;
    private String idNumber;
}

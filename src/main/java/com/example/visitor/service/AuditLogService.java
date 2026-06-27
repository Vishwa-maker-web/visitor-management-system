package com.example.visitor.service;

import com.example.visitor.entity.AuditLog;
import com.example.visitor.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository repository;

    public void saveLog(String email, String event, String location) {

        try {
            System.out.println("entered saveLog");


        AuditLog log = new AuditLog();

        log.setVisitorEmail(email);
        log.setEvent(event);
        log.setLocation(location);
        log.setTime(LocalDateTime.now());

        repository.save(log);
        System.out.println("database saveLog");
    } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<AuditLog> getLogs() {
        return repository.findAll();
    }
}
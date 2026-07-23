package com.example.visitor.controller;

import com.example.visitor.entity.AuditLog;
import com.example.visitor.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://13.206.110.245")
@RestController
@RequestMapping("/audit")
public class AuditLogController {

    @Autowired
    private AuditLogService service;

    @GetMapping("/all")
    public List<AuditLog> getLogs() {
        return service.getLogs();
    }
}
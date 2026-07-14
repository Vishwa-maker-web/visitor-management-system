package com.example.visitor.service;

import com.example.visitor.entity.IndoorRoute;
import com.example.visitor.entity.MeetingAssignment;
import com.example.visitor.repository.IndoorRouteRepository;
import com.example.visitor.repository.MeetingAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndoorRouteService {

    @Autowired
    private MeetingAssignmentRepository meetingAssignmentRepository;

    @Autowired
    private AuditLogService auditLogService;


    @Autowired
    private IndoorRouteRepository indoorRouteRepository;

    public String getRoute(String email) {

        MeetingAssignment meeting =
                meetingAssignmentRepository.findFirstByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Meeting not found"));

        IndoorRoute route =
                indoorRouteRepository.findFirstByRoom(meeting.getRoom())
                        .orElseThrow(() -> new RuntimeException("Route not found"));

        auditLogService.saveLog(email,"navigation started","visitor");
        return route.getRoute();
    }


}
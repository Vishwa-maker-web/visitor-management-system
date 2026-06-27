package com.example.visitor.service;

import com.example.visitor.entity.IndoorRoute;
import com.example.visitor.entity.MeetingAssignment;
import com.example.visitor.repository.IndoorRouteRepository;
import com.example.visitor.repository.MeetingAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndoorRouteService {

    @Autowired
    private MeetingAssignmentRepository meetingAssignmentRepository;

    @Autowired
    private IndoorRouteRepository indoorRouteRepository;

    public String getRoute(String email) {
        MeetingAssignment meeting =
                meetingAssignmentRepository.findByEmail(email).orElseThrow();

        System.out.println("Meeting room = " + meeting.getRoom());

        IndoorRoute route =
                indoorRouteRepository.findByRoom(meeting.getRoom()).orElseThrow();

        System.out.println("Route = " + route.getRoute());
        return route.getRoute();
    }
}
package com.example.visitor.service;

import com.example.visitor.entity.MeetingAssignment;
import com.example.visitor.entity.ReceptionQueue;
import com.example.visitor.repository.MeetingAssignmentRepository;
import com.example.visitor.repository.ReceptionQueueRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceptionService {
    @Autowired
private ReceptionQueueRepository receptionQueueRepository;
    @Autowired
    private MeetingAssignmentRepository meetingAssignmentRepository;

    public List<ReceptionQueue> getWaitingVisitors(){
        return receptionQueueRepository.findByStatus("waiting");
    }



    public String approveVisitor(String email){
        ReceptionQueue visitor = receptionQueueRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("visitor not found"));
        visitor.setStatus("approved");
        receptionQueueRepository.save(visitor);
        return "visitor approved";
    }

    public String assignRoom(String email) {

        MeetingAssignment meeting = new MeetingAssignment();

        meeting.setEmail(email);
        meeting.setRoom("A101");
        meeting.setNfcTag("NFC-1001");
        meeting.setStatus("Assigned");
        System.out.println("saving meeting");

        meetingAssignmentRepository.save(meeting);
        System.out.println("saved");

        return "Meeting Room Assigned\nRoom : A101\nNFC Tag : NFC-1001";
    }

}

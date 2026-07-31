package com.example.visitor.service;
import com.example.visitor.repository.GateEntryRepository;
import com.example.visitor.entity.MeetingAssignment;
import com.example.visitor.entity.MeetingRoom;
import com.example.visitor.entity.GateEntry;
import com.example.visitor.entity.ReceptionQueue;
import com.example.visitor.repository.MeetingAssignmentRepository;
import com.example.visitor.repository.MeetingRoomRepository;
import com.example.visitor.repository.ReceptionQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceptionService {

    @Autowired
    private ReceptionQueueRepository receptionQueueRepository;

    @Autowired
    private MeetingAssignmentRepository meetingAssignmentRepository;

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private NotificationService notificationService;
 
    @Autowired
    private GateEntryRepository gateEntryRepository;

    public List<ReceptionQueue> getWaitingVisitors() {
        return receptionQueueRepository.findAll();
    }

  public String verifyAndNotifyHost(String email) {

        ReceptionQueue visitor = receptionQueueRepository
                .findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() ->
                        new RuntimeException("Visitor not found"));

        if (visitor.getPurpose() == null ||
                visitor.getPurpose().trim().isEmpty()) {

            return "Visitor purpose not available";
        }

        visitor.setStatus("HOST_PENDING");
        receptionQueueRepository.save(visitor);

        notificationService.sendNotification(
                email,
                "Visitor " + visitor.getName()
                        + " is waiting at reception. Purpose: "
                        + visitor.getPurpose()
        );

        return "Host notified. Waiting for confirmation";
    }
    public String deleteReception(String email) {

        ReceptionQueue queue = receptionQueueRepository
                .findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));

        MeetingAssignment meeting = meetingAssignmentRepository
                .findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        if (!meeting.getStatus().equals("COMPLETED")) {
            return "Meeting not completed by host";
        }

        MeetingRoom room = meetingRoomRepository
                .findByRoomName(meeting.getRoom())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setStatus("AVAILABLE");
        meetingRoomRepository.save(room);
               GateEntry gateEntry = gateEntryRepository.findTopByEmailOrderByIdDesc(email)
                        .orElseThrow(()-> new RuntimeException("Gate entry not found"));
        gateEntryRepository.delete(gateEntry);
        receptionQueueRepository.delete(queue);
  
        return "Reception entry deleted and room released";
    }
}

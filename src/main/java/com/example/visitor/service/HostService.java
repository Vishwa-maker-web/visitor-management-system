package com.example.visitor.service;

import com.example.visitor.entity.MeetingAssignment;
import com.example.visitor.entity.MeetingRoom;
import com.example.visitor.entity.ReceptionQueue;
import com.example.visitor.repository.MeetingAssignmentRepository;
import com.example.visitor.repository.MeetingRoomRepository;
import com.example.visitor.repository.ReceptionQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostService {

    @Autowired
    private ReceptionQueueRepository receptionQueueRepository;

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private MeetingAssignmentRepository meetingAssignmentRepository;

    public String confirmVisitor(String email) {

        ReceptionQueue visitor = receptionQueueRepository
                .findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() ->
                        new RuntimeException("Visitor not found"));

        if (!visitor.getStatus().equals("HOST_PENDING")) {
            return "Host notification not sent";
        }

        visitor.setStatus("HOST_APPROVED");

        List<MeetingRoom> rooms =
                meetingRoomRepository.findByStatus("AVAILABLE");

        if (rooms.isEmpty()) {

            visitor.setStatus("WAITING");
            receptionQueueRepository.save(visitor);

            return "No Meeting Rooms Available. Visitor added to waiting queue.";
        }

        MeetingRoom room = rooms.get(0);

        room.setStatus("OCCUPIED");
        meetingRoomRepository.save(room);

        MeetingAssignment meeting = new MeetingAssignment();
        meeting.setEmail(email);
        meeting.setRoom(room.getRoomName());
        meeting.setNfcTag("NFC-" + room.getRoomName());
        meeting.setStatus("ASSIGNED");

        meetingAssignmentRepository.save(meeting);

        visitor.setStatus("ROOM_ASSIGNED");
        receptionQueueRepository.save(visitor);

        return "Meeting Room Assigned : " + room.getRoomName();
    }

public String completeMeeting(String email) {

    MeetingAssignment meeting = meetingAssignmentRepository
            .findTopByEmailOrderByIdDesc(email)
            .orElseThrow(() ->
                    new RuntimeException("Meeting not found"));

    if (!meeting.getStatus().equals("ASSIGNED")) {
        return "Meeting already completed";
    }

    meeting.setStatus("COMPLETED");
    meetingAssignmentRepository.save(meeting);
    ReceptionQueue visitor = receptionQueueRepository
        .findTopByEmailOrderByIdDesc(email)
        .orElseThrow(() -> new RuntimeException("Visitor not found"));

visitor.setStatus("COMPLETED");
receptionQueueRepository.save(visitor);
    return "Meeting completed successfully";
}
}

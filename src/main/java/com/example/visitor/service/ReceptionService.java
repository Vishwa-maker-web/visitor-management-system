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
public class ReceptionService {

    @Autowired
    private ReceptionQueueRepository receptionQueueRepository;

    @Autowired
    private MeetingAssignmentRepository meetingAssignmentRepository;

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private NotificationService notificationService;

    public List<ReceptionQueue> getWaitingVisitors() {
        return receptionQueueRepository.findAll();
    }

    public String verifyAndNotifyHost(String email) {

        ReceptionQueue visitor = receptionQueueRepository
                .findByEmail(email)
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

    public String hostConfirm(String email) {

        ReceptionQueue visitor = receptionQueueRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Visitor not found"));

        if (!visitor.getStatus().equals("HOST_PENDING")) {
            return "Host notification not sent";
        }

        visitor.setStatus("HOST_APPROVED");
        receptionQueueRepository.save(visitor);

        return "Host confirmed visitor";
    }

    public String assignRoom(String email) {

        ReceptionQueue visitor = receptionQueueRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Visitor not found"));

        if (!visitor.getStatus().equals("HOST_APPROVED")) {
            return "Host confirmation required";
        }

        List<MeetingRoom> rooms =
                meetingRoomRepository.findByStatus("AVAILABLE");

        if (rooms.isEmpty()) {
            return "No Meeting Rooms Available";
        }

        MeetingRoom room = rooms.get(0);

        room.setStatus("OCCUPIED");
        meetingRoomRepository.save(room);

        MeetingAssignment meeting = new MeetingAssignment();
        meeting.setEmail(email);
        meeting.setRoom(room.getRoomName());
        meeting.setNfcTag("NFC-" + room.getRoomName());
        meeting.setStatus("Assigned");

        meetingAssignmentRepository.save(meeting);

        visitor.setStatus("ROOM_ASSIGNED");
        receptionQueueRepository.save(visitor);

        return "Meeting Room Assigned\n"
                + "Room : " + room.getRoomName() + "\n"
                + "NFC Tag : NFC-" + room.getRoomName();
    }
}
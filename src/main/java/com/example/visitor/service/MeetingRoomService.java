package com.example.visitor.service;

import com.example.visitor.entity.MeetingRoom;
import com.example.visitor.repository.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingRoomService {


        @Autowired
        private MeetingRoomRepository meetingRoomRepository;

        public List<MeetingRoom> getRooms() {
            return meetingRoomRepository.findAll();
        }

    public String occupyRoom(String roomName) {
        MeetingRoom room = meetingRoomRepository.findByRoomName(roomName)
                .orElseThrow();

        room.setStatus("OCCUPIED");
        meetingRoomRepository.save(room);

        return "Room Occupied";
    }

    public String releaseRoom(String roomName) {
        MeetingRoom room = meetingRoomRepository.findByRoomName(roomName)
                .orElseThrow();

        room.setStatus("AVAILABLE");
        meetingRoomRepository.save(room);

        return "Room Released";
    }
   public String assignRoom() {

        List<MeetingRoom> rooms = meetingRoomRepository.findByStatus("AVAILABLE");

        if (rooms.isEmpty()) {
            return "No rooms available";
        }

        MeetingRoom room = rooms.get(0);

        room.setStatus("OCCUPIED");
        meetingRoomRepository.save(room);

        return "Assigned Room : " + room.getRoomName();
    }
    public String terminateMeeting(String roomName) {

        MeetingRoom room = meetingRoomRepository.findByRoomName(roomName)
                .orElseThrow();

        room.setStatus("AVAILABLE");
        meetingRoomRepository.save(room);

        return "Meeting Terminated";
    }

    public String extendMeeting(String roomName) {

        MeetingRoom room = meetingRoomRepository.findByRoomName(roomName)
                .orElseThrow();

        return "Meeting in " + room.getRoomName() + " extended by 30 minutes";
    }
}

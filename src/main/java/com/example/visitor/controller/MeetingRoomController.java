package com.example.visitor.controller;

import com.example.visitor.entity.MeetingRoom;
import com.example.visitor.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://13.206.110.245")
@RestController
@RequestMapping("room")
public class MeetingRoomController {
        @Autowired
        private MeetingRoomService meetingRoomService;

        @GetMapping("/all")
        public List<MeetingRoom> getRooms() {
            return meetingRoomService.getRooms();
        }


    @PostMapping("/occupy")
    public String occupyRoom(@RequestParam String roomName) {
        return meetingRoomService.occupyRoom(roomName);
    }


    @PostMapping("/release")
    public String releaseRoom(@RequestParam String roomName) {
        return meetingRoomService.releaseRoom(roomName);
    }


    @PostMapping("/assign")
    public String assignRoom() {
        return meetingRoomService.assignRoom();
    }

    @PostMapping("/terminate")
    public String terminateMeeting(@RequestParam String roomName) {
        return meetingRoomService.terminateMeeting(roomName);
    }

    @PostMapping("/extend")
    public String extendMeeting(@RequestParam String roomName) {
        return meetingRoomService.extendMeeting(roomName);
    }
}

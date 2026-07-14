package com.example.visitor.controller;

import com.example.visitor.entity.ReceptionQueue;
import com.example.visitor.service.ReceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reception")
@CrossOrigin(origins = "http://localhost:4200")
public class ReceptionController {

    @Autowired
    private ReceptionService receptionService;

    @GetMapping("/queue")
    public List<ReceptionQueue> getQueue() {
        return receptionService.getWaitingVisitors();
    }

    @PostMapping("/verify")
    public String verifyVisitor(@RequestParam String email) {
        return receptionService.verifyAndNotifyHost(email);
    }

    @PostMapping("/host-confirm")
    public String hostConfirm(@RequestParam String email) {
        return receptionService.hostConfirm(email);
    }

    @PostMapping("/assign-room")
    public String assignRoom(@RequestParam String email) {
        return receptionService.assignRoom(email);
    }
}
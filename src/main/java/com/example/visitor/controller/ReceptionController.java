package com.example.visitor.controller;

import com.example.visitor.entity.ReceptionQueue;
import com.example.visitor.service.ReceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reception")
public class ReceptionController {
    @Autowired
    public ReceptionService receptionService;

    @GetMapping("queue")
    public List<ReceptionQueue> getQueue(){
        return receptionService.getWaitingVisitors();
    }


    @PostMapping("/approve")
    public String approve(@RequestParam String email){
        return receptionService.approveVisitor(email);
    }
    @PostMapping("/assign-room")
    public String assignRoom(@RequestParam String email) {
        return receptionService.assignRoom(email);
    }

}

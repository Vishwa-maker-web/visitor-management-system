package com.example.visitor.controller;

import com.example.visitor.entity.Notification;
import com.example.visitor.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @PostMapping("/send")
    public String send(@RequestParam String email,
                       @RequestParam String message){

        return service.sendNotification(email,message);

    }

    @GetMapping("/all")
    public List<Notification> getAll(){

        return service.getAllNotifications();

    }

    @PutMapping("/read")
    public String read(@RequestParam Long id){

        return service.markAsRead(id);

    }

}
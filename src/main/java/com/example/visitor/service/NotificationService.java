package com.example.visitor.service;

import com.example.visitor.entity.Notification;
import com.example.visitor.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repository;

    public String sendNotification(String email,String message){

        Notification notification = new Notification();

        notification.setHostEmail(email);
        notification.setMessage(message);
        notification.setTime(LocalDateTime.now());
        notification.setStatus("UNREAD");

        repository.save(notification);

        return "Notification Sent";

    }

    public List<Notification> getAllNotifications(){

        return repository.findAll();

    }

    public String markAsRead(Long id){

        Notification notification = repository.findById(id)
                .orElseThrow();

        notification.setStatus("READ");

        repository.save(notification);

        return "Notification Read";

    }

}
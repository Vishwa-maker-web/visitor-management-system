package com.example.visitor.controller;

import com.example.visitor.entity.VisitorTracking;
import com.example.visitor.service.VisitorTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracking")
public class VisitorTrackingController {

    @Autowired
    private VisitorTrackingService visitorTrackingService;

    @PostMapping("/update")
    public String updateLocation(@RequestParam String email,
                                 @RequestParam String zone) {

        return visitorTrackingService.updateZone(email, zone);
    }

    @GetMapping("/all")
    public List<VisitorTracking> getAllVisitors() {
        return visitorTrackingService.getAllVisitors();
    }


    @GetMapping("/timeout")
    public String checkTimeout(@RequestParam String email) {
        return visitorTrackingService.checkTimeout(email);
    }
}
package com.example.visitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.visitor.service.HostService;
@CrossOrigin(origins = "http://13.206.110.245")
@RestController
@RequestMapping("/host")
public class HostController {

    @Autowired
    private HostService hostService;

    @PostMapping("/confirm/{email}")
    public String confirm(@PathVariable String email) {
        return hostService.confirmVisitor(email);
    }

    @PostMapping("/complete/{email}")
    public String complete(@PathVariable String email) {
        return hostService.completeMeeting(email);
    }
}

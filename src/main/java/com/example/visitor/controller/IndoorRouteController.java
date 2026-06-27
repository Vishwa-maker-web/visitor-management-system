package com.example.visitor.controller;

import com.example.visitor.service.IndoorRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/route")
public class IndoorRouteController {

    @Autowired
    private IndoorRouteService indoorRouteService;

    @GetMapping
    public String route(@RequestParam String email) {

        return indoorRouteService.getRoute(email);

    }
}
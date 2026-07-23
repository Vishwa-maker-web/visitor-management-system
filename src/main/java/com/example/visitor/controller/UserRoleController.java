package com.example.visitor.controller;

import com.example.visitor.entity.UserRole;
import com.example.visitor.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://13.206.110.245")
@RestController
@RequestMapping("/auth")
public class UserRoleController {

    @Autowired
    private UserRoleService service;

    @PostMapping("/register")
    public String register(@RequestBody UserRole user){

        return service.register(user);

    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password){

        return service.login(username,password);

    }

}
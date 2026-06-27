package com.example.visitor.controller;

import com.example.visitor.dto.VerifyOtpRequest;
import com.example.visitor.entity.Visitor;
import com.example.visitor.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {
    @Autowired
    private VisitorService visitorService;

    @PostMapping("/register")
    public String register(@RequestBody Visitor visitor){
        return visitorService.register(visitor);
    }
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody VerifyOtpRequest request){
      return visitorService.verifyOtp(
              request.getEmail(),
              request.getOtp()
      );
    }
}

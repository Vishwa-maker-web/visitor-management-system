package com.example.visitor.service;

import com.example.visitor.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {
    @Autowired
    private OtpRepository otpRepository;

    public String generateOtp(){
        return String.valueOf(100000+new Random().nextInt(900000));
    }
}

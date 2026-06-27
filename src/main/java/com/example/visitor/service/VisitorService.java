package com.example.visitor.service;

import com.example.visitor.entity.Blacklist;
import com.example.visitor.entity.OtpVerification;
import com.example.visitor.entity.Visitor;
import com.example.visitor.repository.BlacklistRepository;
import com.example.visitor.repository.OtpRepository;
import com.example.visitor.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VisitorService {
    @Autowired
    private VisitorRepository visitorRepository;
    @Autowired
    private BlacklistRepository blacklistRepository;
    @Autowired
    private OtpService otpservice;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private QrService qrService;

    public String register(Visitor visitor) {

        Optional<Blacklist> blocked = blacklistRepository.findByEmailOrPhone(
                visitor.getEmail(), visitor.getPhone());
        if (blocked.isPresent()){
            return "Access Denied - Blacklisted Visited";
        }

        Optional<Visitor> existing = visitorRepository.findByEmailOrPhone(
                visitor.getEmail(),visitor.getPhone());
        if (existing.isPresent()){
            return "Visitor already exists";
        }
        String otp = otpservice.generateOtp();
        OtpVerification otpData = new OtpVerification();

        otpData.setEmail(visitor.getEmail());
        otpData.setOtp(otp);
        visitor.setPassExpiry(LocalDateTime.now().plusHours(8));
        visitorRepository.save(visitor);
        otpRepository.save(otpData);

        return "Otp generated : "+otp;
    }


    public String verifyOtp(String email, String otp) {
        Optional<OtpVerification> data = otpRepository.findByEmailOrOtp(email, otp);
        if (data.isPresent()){
            String qrData =  "EMAIL = " + email + " ,EXPIRY = " + LocalDateTime.now().plusHours(8);
            String qrPath = qrService.generatQr(qrData);
            return "OTP verified success, Qr Created : "+qrPath;
        }
        return "invalid OTP";
    }
}

package com.example.visitor.service;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.verify-service-sid}")
    private String serviceSid;

    public void sendOtp(String phone) {

        Twilio.init(accountSid, authToken);

        Verification verification =
                Verification.creator(
                        serviceSid,
                        phone,
                        "sms"
                ).create();

        System.out.println("OTP Status: " + verification.getStatus());
    }
    public boolean verifyOtp(String phone, String otp) {

        Twilio.init(accountSid, authToken);

        VerificationCheck verificationCheck =
                VerificationCheck.creator(serviceSid)
                        .setTo(phone)
                        .setCode(otp)
                        .create();

        return "approved".equals(verificationCheck.getStatus());
    }
}
package com.example.visitor.service;

import com.example.visitor.util.AESUtil;
import org.springframework.stereotype.Service;

@Service
public class QrSecurityService {

    public String generateQR(String email) throws Exception {

        return AESUtil.encrypt(email);

    }

    public String verifyQR(String encrypted) throws Exception {

        return AESUtil.decrypt(encrypted);

    }

}
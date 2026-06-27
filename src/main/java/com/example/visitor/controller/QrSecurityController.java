package com.example.visitor.controller;

import com.example.visitor.service.QrSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qr")
public class QrSecurityController {

    @Autowired
    private QrSecurityService service;

    @GetMapping("/encrypt")
    public String encrypt(@RequestParam String email) throws Exception {

        return service.generateQR(email);

    }

    @GetMapping("/decrypt")
    public String decrypt(@RequestParam String data) throws Exception {

        return service.verifyQR(data);

    }

}
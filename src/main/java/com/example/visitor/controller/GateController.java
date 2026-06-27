package com.example.visitor.controller;

import com.example.visitor.service.GateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gate")
public class GateController {
    @Autowired
    private GateService gateService;
    @PostMapping("/scan")
public String scanQr(@RequestParam String email, @RequestParam String idNumber){
    return gateService.scanQr(email,idNumber);
}
}

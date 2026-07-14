package com.example.visitor.controller;

import com.example.visitor.service.GateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
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

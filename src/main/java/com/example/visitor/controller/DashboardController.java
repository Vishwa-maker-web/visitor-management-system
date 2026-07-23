package com.example.visitor.controller;

import com.example.visitor.dto.DailyVisitorDTO;
import com.example.visitor.dto.DashboardSummaryDTO;
import com.example.visitor.dto.LocationCountDTO;
import com.example.visitor.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins = "http://13.206.110.245")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardSummaryDTO getSummary() {
        return dashboardService.getSummary();
    }

    @GetMapping("/high-traffic")
    public List<LocationCountDTO> getHighTrafficRoutes(){
        return dashboardService.getHighTrafficRoutes();
    }








    @GetMapping("/daily-visitors")
    public List<DailyVisitorDTO> getDailyVisitors(){
        return dashboardService.getDailyVisitors();
    }
}


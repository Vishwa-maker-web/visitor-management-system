package com.example.visitor.service;
import com.example.visitor.entity.VisitorTracking;
import com.example.visitor.repository.VisitorTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitorTrackingService {

    @Autowired
    private VisitorTrackingRepository visitorTrackingRepository;
    @Autowired
    private AuditLogService auditLogService;

    public String updateZone(String email, String zone) {

        VisitorTracking tracking = visitorTrackingRepository
                .findByEmail(email)
                .orElse(new VisitorTracking());

        tracking.setEmail(email);
        tracking.setCurrentZone(zone);
        tracking.setLastUpdated(LocalDateTime.now());

        tracking.setEmail(email);
        tracking.setCurrentZone(zone);
        tracking.setLastUpdated(LocalDateTime.now());

        zone = zone.trim();
        System.out.println("Zone = '" + zone + "'");

        if (zone.equalsIgnoreCase("Server Room")
                || zone.equalsIgnoreCase("Admin Cabin")) {

            System.out.println("Restricted Block");

            tracking.setStatus("RESTRICTED");
            visitorTrackingRepository.save(tracking);

            auditLogService.saveLog(email, "Restricted Zone Entry", zone);

            return "Visitor entered Restricted Zone";
        }
        tracking.setStatus("ACTIVE");
        visitorTrackingRepository.save(tracking);
        auditLogService.saveLog(email,"Zone Updated", zone);
        System.out.println("saved");


        return "Visitor location updated";
    }

    public List<VisitorTracking> getAllVisitors() {
        return visitorTrackingRepository.findAll();
    }


    public String checkTimeout(String email) {

    VisitorTracking tracking = visitorTrackingRepository
            .findByEmail(email)
            .orElseThrow();

    if (tracking.getLastUpdated().plusMinutes(30).isBefore(LocalDateTime.now())) {

        tracking.setStatus("TIMEOUT");
        visitorTrackingRepository.save(tracking);

        return "Visitor timed out";
    }

    return "Visitor is still active";
}

}
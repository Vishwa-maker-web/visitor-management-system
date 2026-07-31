package com.example.visitor.service;
import com.example.visitor.entity.Blacklist;
import com.example.visitor.repository.BlacklistRepository;
import com.example.visitor.entity.GateEntry;
import com.example.visitor.entity.ReceptionQueue;
import com.example.visitor.entity.Visitor;
import com.example.visitor.repository.GateEntryRepository;
import com.example.visitor.repository.ReceptionQueueRepository;
import com.example.visitor.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GateService {
    @Autowired
private VisitorRepository visitorRepository;
    @Autowired
private GateEntryRepository gateEntryRepository;
    @Autowired
private ReceptionQueueRepository receptionQueueRepository;
    @Autowired
    AuditLogService auditLogService;
    @Autowired
private BlacklistRepository blacklistRepository;

    public String scanQr(String email, String idNumber){

        Optional<Visitor> visitor = visitorRepository.findByEmail(email);
        if (visitor.isEmpty()){
            return "visitor not found";
        }
        Visitor v = visitor.get();
        if (v.getPassExpiry().isBefore(LocalDateTime.now())){
            return "pass expired";
        }
        if (!v.getIdNumber().equals(idNumber)){
            return "invalid id";
        }
   Optional<Blacklist> blacklisted = blacklistRepository.findByEmailOrPhone(v.getEmail(), v.getPhone());
        if (blacklisted.isPresent()){
            return "visitor blacklisted";
       }

        Optional<GateEntry> existingEntry = gateEntryRepository.findTopByEmailOrderByIdDesc(email);

        if (existingEntry.isPresent()) {
            return "Visitor already entered";
        }

        GateEntry entry = new GateEntry();
        entry.setEmail(email);
        entry.setGateInTime(LocalDateTime.now());
        gateEntryRepository.save(entry);
        ReceptionQueue queue = new ReceptionQueue();
        queue.setEmail(v.getEmail());
        queue.setName(v.getName());
        queue.setPurpose(v.getPurpose());
        queue.setStatus("waiting");
        receptionQueueRepository.save(queue);
        auditLogService.saveLog(email,"gate allowed","system");

        return "gate entry allowed";
    }
}

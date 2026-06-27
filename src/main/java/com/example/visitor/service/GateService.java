package com.example.visitor.service;

import com.example.visitor.entity.GateEntry;
import com.example.visitor.entity.ReceptionQueue;
import com.example.visitor.entity.Visitor;
import com.example.visitor.repository.GateEntryRepository;
import com.example.visitor.repository.ReceptionQueueRepository;
import com.example.visitor.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GateService {
    @Autowired
private VisitorRepository visitorRepository;
    @Autowired
private GateEntryRepository gateEntryRepository;
    @Autowired
private ReceptionQueueRepository receptionQueueRepository;

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
        return "gate entry allowed";
    }
}

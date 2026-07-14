package com.example.visitor.service;

import com.example.visitor.dto.DailyVisitorDTO;
import com.example.visitor.dto.DashboardSummaryDTO;
import com.example.visitor.dto.LocationCountDTO;
import com.example.visitor.repository.AuditLogRepository;
import com.example.visitor.repository.MeetingRoomRepository;
import com.example.visitor.repository.VisitorRepository;
import com.example.visitor.repository.VisitorTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private VisitorTrackingRepository trackingRepository;

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private VisitorRepository visitorRepository;


    public DashboardSummaryDTO getSummary() {

        DashboardSummaryDTO dto = new DashboardSummaryDTO();

        dto.setTotalVisitors(visitorRepository.count());
        dto.setActiveVisitors(trackingRepository.countByStatus("ACTIVE"));
        dto.setRestrictedVisitors(trackingRepository.countByStatus("RESTRICTED"));
        dto.setTimeoutVisitors(trackingRepository.countByStatus("TIMEOUT"));
        dto.setAvailableRooms(meetingRoomRepository.countByStatus("AVAILABLE"));

        return dto;
    }

    public List<LocationCountDTO> getHighTrafficRoutes(){
        return auditLogRepository.getHighTrafficRoutes();
    }

    public List<DailyVisitorDTO> getDailyVisitors() {

        List<Object[]> rows = auditLogRepository.getDailyVisitors();

        List<DailyVisitorDTO> result = new ArrayList<>();

        for (Object[] row : rows) {
            result.add(new DailyVisitorDTO(
                    row[0].toString(),
                    ((Number) row[1]).longValue()
            ));
        }

        return result;
    }
}
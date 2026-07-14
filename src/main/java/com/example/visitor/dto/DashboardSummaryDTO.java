package com.example.visitor.dto;

import lombok.Data;

@Data
public class DashboardSummaryDTO {

    private long totalVisitors;
    private long activeVisitors;
    private long restrictedVisitors;
    private long timeoutVisitors;
    private long availableRooms;

    public DashboardSummaryDTO() {
    }

    public DashboardSummaryDTO(long totalVisitors, long activeVisitors,
                               long restrictedVisitors, long timeoutVisitors,
                               long availableRooms) {
        this.totalVisitors = totalVisitors;
        this.activeVisitors = activeVisitors;
        this.restrictedVisitors = restrictedVisitors;
        this.timeoutVisitors = timeoutVisitors;
        this.availableRooms = availableRooms;
    }

}
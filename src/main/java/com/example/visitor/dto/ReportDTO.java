package com.example.visitor.dto;

import lombok.Data;

@Data
public class ReportDTO {

    private long totalVisitors;
    private long interviewVisitors;
    private long todayVisitors;
    private long restrictedVisitors;

    public ReportDTO() {}

    public ReportDTO(long totalVisitors,
                     long interviewVisitors,
                     long todayVisitors,
                     long restrictedVisitors) {
        this.totalVisitors = totalVisitors;
        this.interviewVisitors = interviewVisitors;
        this.todayVisitors = todayVisitors;
        this.restrictedVisitors = restrictedVisitors;
    }

}
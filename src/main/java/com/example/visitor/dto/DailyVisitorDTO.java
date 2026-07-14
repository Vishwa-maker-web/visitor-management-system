package com.example.visitor.dto;

import lombok.Data;

@Data
public class DailyVisitorDTO {
    private String date;
    private Long count;
    public DailyVisitorDTO(){

    }
    public DailyVisitorDTO(String date,long count){
        this.date= date;
        this.count = count;
    }
}

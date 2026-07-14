package com.example.visitor.dto;

import lombok.Data;

@Data
public class LocationCountDTO {

    private String location;
    private Long count;

    public LocationCountDTO() {
    }

    public LocationCountDTO(String location, Long count) {
        this.location = location;
        this.count = count;
    }

}
package com.example.carManagement.api.dtos;

import java.time.LocalDate;

public class GarageReportDto {

    private LocalDate date;
    private Integer requests;
    private Integer availableCapacity;

    public GarageReportDto(LocalDate date, Integer requests, Integer availableCapacity) {
        this.date = date;
        this.requests = requests;
        this.availableCapacity = availableCapacity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getRequests() {
        return requests;
    }

    public void setRequests(Integer requests) {
        this.requests = requests;
    }

    public Integer getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(Integer availableCapacity) {
        this.availableCapacity = availableCapacity;
    }
}

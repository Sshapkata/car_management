package com.example.carManagement.api.services;

import com.example.carManagement.api.dtos.MaintenanceDto;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceService {

    void crete(MaintenanceDto dto);

    List<MaintenanceDto> findAll(Long carId, Long garageId, LocalDate startDate, LocalDate endDate);
}

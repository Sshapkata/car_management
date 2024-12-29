package com.example.carManagement.api.services;

import com.example.carManagement.api.dtos.MaintenanceDto;
import com.example.carManagement.api.dtos.MaintenanceReportDto;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceService {

    void crete(MaintenanceDto dto);

    List<MaintenanceDto> findAll(Long carId, Long garageId, LocalDate startDate, LocalDate endDate);

    MaintenanceDto findById(Long id);

    void updateById(Long id, MaintenanceDto dto);

    void deleteById(Long id);

    List<MaintenanceReportDto> getReports(Long garageId, String startMonth, String endMonth);
}

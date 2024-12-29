package com.example.carManagement.api.services;

import com.example.carManagement.api.dtos.GarageDto;
import com.example.carManagement.api.dtos.GarageReportDto;

import java.time.LocalDate;
import java.util.List;

public interface GarageService {

    void create(GarageDto dto);

    List<GarageDto> findAll(String city);

    List<GarageDto> findAll();

    GarageDto findById(Long id);

    void updateById(Long id, GarageDto dto);

    void deleteById(Long id);

    List<GarageReportDto> getReports(Long garageId, LocalDate startDate, LocalDate endDate);

}

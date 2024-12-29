package com.example.carManagement.api.controllers;

import com.example.carManagement.api.dtos.MaintenanceDto;
import com.example.carManagement.api.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping
    public List<MaintenanceDto> findAll(@RequestParam(required = false) Long carId,
                                @RequestParam(required = false) Long garageId,
                                @RequestParam(required = false) LocalDate startDate,
                                @RequestParam(required = false) LocalDate endDate){
        return maintenanceService.findAll(carId, garageId, startDate, endDate);
    }

    @PostMapping
    public void createMaintenance(@RequestBody MaintenanceDto dto){
        maintenanceService.crete(dto);
    }
}

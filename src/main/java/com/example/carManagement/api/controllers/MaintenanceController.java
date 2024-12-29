package com.example.carManagement.api.controllers;

import com.example.carManagement.api.dtos.CarDto;
import com.example.carManagement.api.dtos.MaintenanceDto;
import com.example.carManagement.api.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/{id}")
    public MaintenanceDto findById(@PathVariable Long id){
        return maintenanceService.findById(id);
    }

    @PostMapping
    public void createMaintenance(@RequestBody MaintenanceDto dto){
        maintenanceService.crete(dto);
    }

    @PutMapping("/{id}")
    public void updateCar(@PathVariable Long id, @RequestBody MaintenanceDto dto){
        maintenanceService.updateById(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteGarage(@PathVariable Long id){
        maintenanceService.deleteById(id);
    }
}

package com.example.carManagement.api.controllers;

import com.example.carManagement.api.dtos.GarageDto;
import com.example.carManagement.api.services.GarageService;
import jakarta.validation.Valid;
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

import java.util.List;

@RestController
@RequestMapping("/garages")
public class GarageController {

    @Autowired
    private GarageService garageService;

    @GetMapping
    public List<GarageDto> findAll(@RequestParam(required = false) String city) {
        if (city != null) {
            return garageService.findAll(city);
        }
        return garageService.findAll();
    }

    @GetMapping("/{id}")
    public GarageDto findById(@PathVariable Long id) {
        return garageService.findById(id);
    }

    @PostMapping
    public void createGarage(@Valid @RequestBody GarageDto dto){
        garageService.create(dto);
    }

    @PutMapping("/{id}")
    public void updateGarage(@PathVariable Long id,@Valid @RequestBody GarageDto dto){
        garageService.updateById(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteGarage(@PathVariable Long id){
        garageService.deleteById(id);
    }
}

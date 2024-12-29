package com.example.carManagement.api.controllers;

import com.example.carManagement.api.dtos.CarDto;
import com.example.carManagement.api.dtos.GarageDto;
import com.example.carManagement.api.services.CarService;
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
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public List<CarDto> findAll(@RequestParam(required = false) String carMake,
                                @RequestParam(required = false) Long garageId,
                                @RequestParam(required = false) Integer fromYear,
                                @RequestParam(required = false) Integer toYear){
        return carService.findAll(carMake, garageId, fromYear, toYear);
    }

    @GetMapping("/{id}")
    public CarDto findById(@PathVariable Long id){
        return carService.findById(id);
    }

    @PostMapping
    public void createCar(@Valid @RequestBody CarDto dto){
        carService.create(dto);
    }

    @PutMapping("/{id}")
    public void updateCar(@PathVariable Long id, @RequestBody CarDto dto){
        carService.updateById(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteGarage(@PathVariable Long id){
        carService.deleteById(id);
    }
}

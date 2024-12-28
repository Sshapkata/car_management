package com.example.carManagement.api.services;

import com.example.carManagement.api.dtos.CarDto;

import java.util.List;

public interface CarService {

    List<CarDto> findAll(String make, Long garageId, Integer fromYear, Integer toYear);

    void create(CarDto dto);

    void updateById(Long id, CarDto dto);
}

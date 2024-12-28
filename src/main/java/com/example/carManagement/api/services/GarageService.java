package com.example.carManagement.api.services;

import com.example.carManagement.api.dtos.GarageDto;

import java.util.List;

public interface GarageService {

    void create(GarageDto dto);

    List<GarageDto> findAll(String city);

    GarageDto findById(Long id);

    void updateById(Long id, GarageDto dto);

    void deleteById(Long id);

}

package com.example.carManagement.service.util;

import com.example.carManagement.api.dtos.GarageDto;
import com.example.carManagement.service.entities.GarageEntity;

public class GarageEntityConvertorUtil {

    public static GarageEntity convertToEntity(GarageDto dto){
        return new GarageEntity(dto.getName(), dto.getLocation(), dto.getCity(), dto.getCapacity());
    }

    public static GarageDto convertToDto(GarageEntity entity){
        return new GarageDto(entity.getId(), entity.getName(), entity.getLocation(), entity.getCity(), entity.getCapacity());
    }
}

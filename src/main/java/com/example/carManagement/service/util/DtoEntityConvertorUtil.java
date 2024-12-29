package com.example.carManagement.service.util;

import com.example.carManagement.api.dtos.CarDto;
import com.example.carManagement.api.dtos.GarageDto;
import com.example.carManagement.api.dtos.MaintenanceDto;
import com.example.carManagement.service.entities.CarEntity;
import com.example.carManagement.service.entities.GarageEntity;
import com.example.carManagement.service.entities.MaintenanceEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class DtoEntityConvertorUtil {

    public static GarageEntity convertToEntity(GarageDto dto){
        return new GarageEntity(dto.getName(), dto.getLocation(), dto.getCity(), dto.getCapacity());
    }

    public static GarageDto convertToDto(GarageEntity entity){
        return new GarageDto(entity.getId(), entity.getName(), entity.getLocation(), entity.getCity(), entity.getCapacity());
    }

    public static CarEntity convertToEntity(CarDto dto){
        Set<GarageEntity> garageEntities = dto.getGarageIds()
                .stream()
                .map(GarageEntity::new)
                .collect(Collectors.toSet());
        return new CarEntity(dto.getMake(), dto.getModel(), dto.getProductionYear(), dto.getLicensePlate(), garageEntities);
    }

    public static CarDto convertToDto(CarEntity entity){
        Set<GarageDto> garageDtos = entity.getGarages()
                .stream()
                .map(DtoEntityConvertorUtil::convertToDto)
                .collect(Collectors.toSet());
        return new CarDto(entity.getId(), entity.getMake(), entity.getModel(), entity.getProductionYear(), entity.getLicensePlate(), garageDtos);
    }

    public static MaintenanceEntity convertToEntity(MaintenanceDto dto){
        return new MaintenanceEntity(new CarEntity(dto.getCarId()), new GarageEntity(dto.getGarageId()), dto.getScheduledDate(), dto.getServiceType());
    }

    public static MaintenanceDto convertToDto(MaintenanceEntity entity){
        return new MaintenanceDto(entity.getId(), entity.getCarEntity().getId(), entity.getCarEntity().getMake(), entity.getServiceType(), entity.getScheduledDate(), entity.getGarageEntity().getId(), entity.getGarageEntity().getName());
    }
}

package com.example.carManagement.service.servicesImpl;

import com.example.carManagement.api.dtos.CarDto;
import com.example.carManagement.api.services.CarService;
import com.example.carManagement.service.entities.CarEntity;
import com.example.carManagement.service.entities.GarageEntity;
import com.example.carManagement.service.exceptions.ResourceNotFoundException;
import com.example.carManagement.service.repositories.CarRepository;
import com.example.carManagement.service.util.DtoEntityConvertorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<CarDto> findAll(String make, Long garageId, Integer fromYear, Integer toYear) {
        return carRepository.findCars(make, garageId, fromYear, toYear).stream()
                .map(DtoEntityConvertorUtil::convertToDto)
                .toList();
    }

    @Override
    public void create(CarDto dto) {
        carRepository.save(DtoEntityConvertorUtil.convertToEntity(dto));
    }

    @Override
    public void updateById(Long id, CarDto dto) {
        CarEntity existingCarEntity = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garage with ID" + id + " not found"));

        existingCarEntity.setMake(dto.getMake());
        existingCarEntity.setModel(dto.getModel());
        existingCarEntity.setProductionYear(dto.getProductionYear());
        existingCarEntity.setLicensePlate(dto.getLicensePlate());
        Set<GarageEntity> garageEntities = dto.getGarageIds()
                .stream()
                .map(garageId -> {
                    GarageEntity garageEntity = new GarageEntity();
                    garageEntity.setId(garageId);
                    return garageEntity;
                })
                .collect(Collectors.toSet());
        existingCarEntity.setGarages(garageEntities);
        carRepository.save(existingCarEntity);
    }
}

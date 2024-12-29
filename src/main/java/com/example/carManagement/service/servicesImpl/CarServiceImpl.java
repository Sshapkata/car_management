package com.example.carManagement.service.servicesImpl;

import com.example.carManagement.api.dtos.CarDto;
import com.example.carManagement.api.services.CarService;
import com.example.carManagement.service.entities.CarEntity;
import com.example.carManagement.service.entities.GarageEntity;
import com.example.carManagement.service.exceptions.ResourceNotFoundException;
import com.example.carManagement.service.repositories.CarRepository;
import com.example.carManagement.service.repositories.GarageRepository;
import com.example.carManagement.service.util.DtoEntityConvertorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private GarageRepository garageRepository;

    @Override
    public List<CarDto> findAll(String make, Long garageId, Integer fromYear, Integer toYear) {
        List<CarDto> cars = carRepository.findCars(make, garageId, fromYear, toYear).stream()
                .map(DtoEntityConvertorUtil::convertToDto)
                .toList();
        if (cars.isEmpty()) {
            throw new ResourceNotFoundException("No cars found");
        }
        return cars;
    }

    @Override
    public CarDto findById(Long id) {
        CarEntity carEntity = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car with ID " + id + " not found"));
        return DtoEntityConvertorUtil.convertToDto(carEntity);
    }

    @Override
    public void create(CarDto dto) {
        Set<GarageEntity> garageEntities = new HashSet<>(garageRepository.findAllById(dto.getGarageIds()));
        if (garageEntities.size() != dto.getGarageIds().size()){
            throw new ResourceNotFoundException("Some garages were not found");
        }
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
                .map(GarageEntity::new)
                .collect(Collectors.toSet());
        existingCarEntity.setGarages(garageEntities);
        carRepository.save(existingCarEntity);
    }

    @Override
    public void deleteById(Long id) {
        if (!carRepository.existsById(id)) {
            throw  new ResourceNotFoundException("Car with ID " + id + " not found");
        }
        carRepository.deleteById(id);
    }
}

package com.example.carManagement.service.servicesImpl;

import com.example.carManagement.api.dtos.MaintenanceDto;
import com.example.carManagement.api.services.MaintenanceService;
import com.example.carManagement.service.entities.CarEntity;
import com.example.carManagement.service.entities.GarageEntity;
import com.example.carManagement.service.entities.MaintenanceEntity;
import com.example.carManagement.service.exceptions.GarageCapacityExceededException;
import com.example.carManagement.service.exceptions.ResourceNotFoundException;
import com.example.carManagement.service.repositories.CarRepository;
import com.example.carManagement.service.repositories.GarageRepository;
import com.example.carManagement.service.repositories.MaintenanceRepository;
import com.example.carManagement.service.util.DtoEntityConvertorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private GarageRepository garageRepository;
    @Autowired
    private CarRepository carRepository;

    @Override
    public void crete(MaintenanceDto dto) {
        Optional<GarageEntity> optionalGarage = garageRepository.findById(dto.getGarageId());
        if (optionalGarage.isEmpty()) {
            throw new ResourceNotFoundException("Garage with ID " + dto.getGarageId() + " were not found");
        }
        List<MaintenanceDto> maintenanceDtosForGarageForDate = findAll(null, optionalGarage.get().getId(), dto.getScheduledDate(), dto.getScheduledDate());
        if (maintenanceDtosForGarageForDate.size() >= optionalGarage.get().getCapacity()) {
            throw new GarageCapacityExceededException("For garage " + dto.getGarageName() + " capacity for data " + dto.getScheduledDate() + " is exceeded");
        }

        Optional<CarEntity> optionalCar = carRepository.findById(dto.getCarId());
        if (optionalCar.isEmpty()) {
            throw new ResourceNotFoundException("Car with ID " + dto.getCarId() + " were not found");
        }
        maintenanceRepository.save(DtoEntityConvertorUtil.convertToEntity(dto));
    }

    @Override
    public List<MaintenanceDto> findAll(Long carId, Long garageId, LocalDate startDate, LocalDate endDate) {
        List<MaintenanceDto> maintenances = maintenanceRepository.findAll(carId, garageId, startDate, endDate)
                .stream()
                .map(DtoEntityConvertorUtil::convertToDto)
                .toList();
        if (maintenances.isEmpty()) {
            throw new ResourceNotFoundException("No maintenances found");
        }
        return maintenances;
    }

    @Override
    public MaintenanceDto findById(Long id) {
        MaintenanceEntity maintenanceEntity = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance with ID " + id + " not found"));
        return DtoEntityConvertorUtil.convertToDto(maintenanceEntity);
    }

    @Override
    public void updateById(Long id, MaintenanceDto dto) {
        MaintenanceEntity existingMaintenanceEntity = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance with ID " + id + " not found"));

        existingMaintenanceEntity.setCarEntity(new CarEntity(dto.getCarId()));
        existingMaintenanceEntity.setGarageEntity(new GarageEntity(dto.getGarageId()));
        existingMaintenanceEntity.setScheduledDate(dto.getScheduledDate());
        existingMaintenanceEntity.setServiceType(dto.getServiceType());
        maintenanceRepository.save(existingMaintenanceEntity);
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)){
            throw new ResourceNotFoundException("Maintenance with ID " + id + " not found");
        }
        maintenanceRepository.deleteById(id);
    }
}

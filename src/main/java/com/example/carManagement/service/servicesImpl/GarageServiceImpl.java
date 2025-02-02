package com.example.carManagement.service.servicesImpl;

import com.example.carManagement.api.dtos.GarageDto;
import com.example.carManagement.api.dtos.GarageReportDto;
import com.example.carManagement.api.services.GarageService;
import com.example.carManagement.service.entities.GarageEntity;
import com.example.carManagement.service.entities.MaintenanceEntity;
import com.example.carManagement.service.exceptions.ResourceNotFoundException;
import com.example.carManagement.service.repositories.GarageRepository;
import com.example.carManagement.service.repositories.MaintenanceRepository;
import com.example.carManagement.service.util.DtoEntityConvertorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GarageServiceImpl implements GarageService {

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Override
    public List<GarageDto> findAll(String city) {
        List<GarageEntity> garages = garageRepository.findByCity(city);

        if (garages.isEmpty()) {
            throw new ResourceNotFoundException("No garages found in city: " + city);
        }
        return garages.stream()
                .map(DtoEntityConvertorUtil::convertToDto)
                .toList();
    }

    @Override
    public List<GarageDto> findAll() {
        return garageRepository.findAll().stream()
                .map(DtoEntityConvertorUtil::convertToDto)
                .toList();
    }

    @Override
    public GarageDto findById(Long id) {
        GarageEntity garageEntity = garageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garage with ID " + id + " not found"));
        return DtoEntityConvertorUtil.convertToDto(garageEntity);
    }

    @Override
    public void create(GarageDto dto) {
        garageRepository.save(DtoEntityConvertorUtil.convertToEntity(dto));
    }

    @Override
    public void updateById(Long id, GarageDto dto) {
        GarageEntity existingGarageEntity = garageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garage with ID " + id + " not found"));

        existingGarageEntity.setName(dto.getName());
        existingGarageEntity.setLocation(dto.getLocation());
        existingGarageEntity.setCity(dto.getCity());
        existingGarageEntity.setCapacity(dto.getCapacity());
        garageRepository.save(existingGarageEntity);
    }

    @Override
    public void deleteById(Long id) {
        if (!garageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Garage with id " + id + " not found.");
        }
        garageRepository.deleteById(id);
    }

    @Override
    public List<GarageReportDto> getReports(Long garageId, LocalDate startDate, LocalDate endDate) {
        GarageDto garageDto = findById(garageId);
        Map<LocalDate, List<MaintenanceEntity>> groupedByDate = maintenanceRepository.findAllByGarageIdAndScheduledDateBetween(garageId, startDate, endDate)
                .stream()
                .collect(Collectors.groupingBy(MaintenanceEntity::getScheduledDate));

        ArrayList<GarageReportDto> garageReportDtos = new ArrayList<>();
        groupedByDate.forEach((localDate, maintenanceEntities) -> {
            garageReportDtos.add(new GarageReportDto(localDate, maintenanceEntities.size(), garageDto.getCapacity() - maintenanceEntities.size()));
        });

        return garageReportDtos;
    }
}

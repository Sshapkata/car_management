package com.example.carManagement.service.servicesImpl;

import com.example.carManagement.api.dtos.GarageDto;
import com.example.carManagement.api.services.GarageService;
import com.example.carManagement.service.entities.GarageEntity;
import com.example.carManagement.service.exceptions.ResourceNotFoundException;
import com.example.carManagement.service.repositories.GarageRepository;
import com.example.carManagement.service.util.GarageEntityConvertorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarageServiceImpl implements GarageService {

    @Autowired
    private GarageRepository garageRepository;

    @Override
    public List<GarageDto> findAll(String city) {
        List<GarageEntity> garages = garageRepository.findByCity(city);

        if (garages.isEmpty()) {
            throw new ResourceNotFoundException("No garages found in city: " + city);
        }
        return garages.stream()
                .map(GarageEntityConvertorUtil::convertToDto)
                .toList();
    }

    @Override
    public GarageDto findById(Long id) {
        GarageEntity garageEntity = garageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garage with ID " + id + " not found"));
        return GarageEntityConvertorUtil.convertToDto(garageEntity);
    }

    @Override
    public void create(GarageDto dto) {
        garageRepository.save(GarageEntityConvertorUtil.convertToEntity(dto));
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
}

package com.example.carManagement.service.servicesImpl;

import com.example.carManagement.api.dtos.MaintenanceDto;
import com.example.carManagement.api.dtos.MaintenanceReportDto;
import com.example.carManagement.api.services.MaintenanceService;
import com.example.carManagement.service.entities.CarEntity;
import com.example.carManagement.service.entities.GarageEntity;
import com.example.carManagement.service.entities.MaintenanceEntity;
import com.example.carManagement.service.exceptions.ResourceNotFoundException;
import com.example.carManagement.service.repositories.CarRepository;
import com.example.carManagement.service.repositories.GarageRepository;
import com.example.carManagement.service.repositories.MaintenanceRepository;
import com.example.carManagement.service.util.DtoEntityConvertorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<MaintenanceReportDto> getReports(Long garageId, String startMonth, String endMonth) {
        YearMonth startYearMonth = YearMonth.parse(startMonth);
        YearMonth endYearMonth = YearMonth.parse(endMonth);

        LocalDate startDate = startYearMonth.atDay(1);
        LocalDate endDate = endYearMonth.atEndOfMonth();

        Map<YearMonth, List<MaintenanceEntity>> groupedByDate = maintenanceRepository.findAllByGarageIdAndScheduledDateBetween(garageId, startDate, endDate)
                .stream().collect(Collectors.groupingBy(entity -> YearMonth.from(entity.getScheduledDate())));

        List<MaintenanceReportDto> maintenanceReportDtoList = new ArrayList<>();

        YearMonth current = startYearMonth;
        while (!current.isAfter(endYearMonth)) {
            MaintenanceReportDto.YearMonthDto yearMonthDto = new MaintenanceReportDto.YearMonthDto();
            yearMonthDto.setYear(current.getYear());
            yearMonthDto.setMonth(current.getMonth());
            yearMonthDto.setLeapYear(current.isLeapYear());
            yearMonthDto.setMonthValue(current.getMonthValue());

            MaintenanceReportDto maintenanceReportDto = new MaintenanceReportDto();

            maintenanceReportDto.setYearMonth(yearMonthDto);
            maintenanceReportDto.setRequests(groupedByDate.get(current) == null ? 0 : groupedByDate.get(current).size());
            maintenanceReportDtoList.add(maintenanceReportDto);

            current = current.plusMonths(1);
        }

        return maintenanceReportDtoList;
    }
}

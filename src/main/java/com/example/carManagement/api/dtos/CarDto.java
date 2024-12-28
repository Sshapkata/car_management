package com.example.carManagement.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

public class CarDto {

    private Long id;
    @NotBlank(message = "Make cannot be blank")
    private String make;
    @NotBlank(message = "Model cannot be blank")
    private String model;
    @NotNull(message = "ProductionYear cannot be blank")
    private Integer productionYear;
    @NotBlank(message = "License Plate cannot be blank")
    private String licensePlate;
//    @NotEmpty(message = "Garages cannot be empty")
    private Set<GarageDto> garages = new HashSet<>();
    private Set<Long> garageIds = new HashSet<>();

    public CarDto(Long id, String make, String model, Integer productionYear, String licensePlate, Set<GarageDto> garages) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.productionYear = productionYear;
        this.licensePlate = licensePlate;
        this.garages = garages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Set<GarageDto> getGarages() {
        return garages;
    }

    public void setGarages(Set<GarageDto> garages) {
        this.garages = garages;
    }

    public Set<Long> getGarageIds() {
        return garageIds;
    }

    public void setGarageIds(Set<Long> garageIds) {
        this.garageIds = garageIds;
    }
}

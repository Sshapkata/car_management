package com.example.carManagement.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GarageDto {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Location cannot be blank")
    private String location;
    @NotBlank(message = "City cannot be blank")
    private String city;
    @NotNull(message = "Capacity cannot be null")
    private Integer capacity;

    public GarageDto(Long id, String name, String location, String city, Integer capacity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.city = city;
        this.capacity = capacity;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}

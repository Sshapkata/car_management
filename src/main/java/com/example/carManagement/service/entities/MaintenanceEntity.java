package com.example.carManagement.service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "Maintenance")
public class MaintenanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private CarEntity carEntity;
    @ManyToOne
    @JoinColumn(name = "garage_id", nullable = false)
    private GarageEntity garageEntity;
    private LocalDate scheduledDate;
    private String serviceType;

    public MaintenanceEntity() {
    }

    public MaintenanceEntity(CarEntity carEntity, GarageEntity garageEntity, LocalDate scheduledDate, String serviceType) {
        this.carEntity = carEntity;
        this.garageEntity = garageEntity;
        this.scheduledDate = scheduledDate;
        this.serviceType = serviceType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarEntity getCarEntity() {
        return carEntity;
    }

    public void setCarEntity(CarEntity carEntity) {
        this.carEntity = carEntity;
    }

    public GarageEntity getGarageEntity() {
        return garageEntity;
    }

    public void setGarageEntity(GarageEntity garageEntity) {
        this.garageEntity = garageEntity;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}

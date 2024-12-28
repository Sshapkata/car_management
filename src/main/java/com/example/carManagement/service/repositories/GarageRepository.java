package com.example.carManagement.service.repositories;

import com.example.carManagement.service.entities.GarageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GarageRepository extends JpaRepository<GarageEntity, Long> {

    List<GarageEntity> findByCity(String city);

}

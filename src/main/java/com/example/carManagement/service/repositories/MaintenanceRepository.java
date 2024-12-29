package com.example.carManagement.service.repositories;

import com.example.carManagement.service.entities.MaintenanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceRepository extends JpaRepository<MaintenanceEntity, Long> {

    @Query("SELECT m FROM MaintenanceEntity m " +
            "WHERE " +
            "(:carId IS NULL OR m.carEntity.id = :carId) AND " +
            "(:garageId IS NULL OR m.garageEntity.id = :garageId) AND " +
            "(:startDate IS NULL OR m.scheduledDate >= :startDate) AND " +
            "(:endDate IS NULL OR m.scheduledDate <= :endDate)")
    List<MaintenanceEntity> findAll(@Param("carId") Long carId,
                                 @Param("garageId") Long garageId,
                                 @Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);

    @Query("SELECT m FROM MaintenanceEntity m " +
            "WHERE m.garageEntity.id = :garageId AND m.scheduledDate BETWEEN :startDate AND :endDate")
    List<MaintenanceEntity> findAllByGarageIdAndScheduledDateBetween(@Param("garageId") Long garageId,
                                                                     @Param("startDate") LocalDate startDate,
                                                                     @Param("endDate") LocalDate endDate);
}

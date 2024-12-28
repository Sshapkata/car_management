package com.example.carManagement.service.repositories;

import com.example.carManagement.service.entities.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<CarEntity, Long> {

    @Query("SELECT c FROM CarEntity c " +
            "LEFT JOIN c.garages g " +
            "WHERE " +
            "(:make IS NULL OR c.make = :make) AND " +
            "(:garageId IS NULL OR g.id = :garageId) AND " +
            "(:fromYear IS NULL OR c.productionYear >= :fromYear) AND " +
            "(:toYear IS NULL OR c.productionYear <= :toYear)")
    List<CarEntity> findCars(@Param("make") String make,
                       @Param("garageId") Long garageId,
                       @Param("fromYear") Integer fromYear,
                       @Param("toYear") Integer toYear);
}

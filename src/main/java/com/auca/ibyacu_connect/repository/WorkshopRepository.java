package com.auca.ibyacu_connect.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.auca.ibyacu_connect.model.Workshop;

@Repository
public interface WorkshopRepository extends JpaRepository<Workshop, UUID> {

    @Query("SELECT w FROM Workshop w WHERE NOT EXISTS (SELECT a FROM Artisan a WHERE a.workshop = w) ORDER BY w.workshopNumber")
    List<Workshop> findFreeWorkshops();

    Optional<Workshop> findByWorkshopNumber(int workshopNumber);

}

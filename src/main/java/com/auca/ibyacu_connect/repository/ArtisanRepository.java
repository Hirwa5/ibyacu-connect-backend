package com.auca.ibyacu_connect.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auca.ibyacu_connect.model.Artisan;

@Repository
public interface ArtisanRepository extends JpaRepository<Artisan, UUID> {

    @Query("SELECT a FROM Artisan a JOIN a.trades t WHERE LOWER(t.name) = LOWER(:name)")
    List<Artisan> findArtisansByTrade(@Param("name") String name);

}

package com.auca.ibyacu_connect.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auca.ibyacu_connect.model.Artisan;

@Repository
public interface ArtisanRepository extends JpaRepository<Artisan, UUID> {

}

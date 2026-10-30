package com.auca.ibyacu_connect.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auca.ibyacu_connect.model.Resident;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, UUID> {

    List<Resident> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

}

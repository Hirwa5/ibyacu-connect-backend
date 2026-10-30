package com.auca.ibyacu_connect.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auca.ibyacu_connect.model.RequestStatus;
import com.auca.ibyacu_connect.model.ServiceRequest;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, UUID> {

    List<ServiceRequest> findByStatusOrderByRequestDateAsc(RequestStatus status);

    List<ServiceRequest> findByRequestDateBetweenOrderByRequestDateAsc(LocalDate start, LocalDate end);

}

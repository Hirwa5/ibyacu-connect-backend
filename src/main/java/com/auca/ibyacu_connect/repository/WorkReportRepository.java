package com.auca.ibyacu_connect.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auca.ibyacu_connect.model.ServiceRequest;
import com.auca.ibyacu_connect.model.WorkReport;

@Repository
public interface WorkReportRepository extends JpaRepository<WorkReport, UUID> {

    @Query("SELECT d FROM WorkReport d WHERE d.serviceRequest.resident.nationalId = :nationalId ORDER BY d.reportDate DESC")
    List<WorkReport> findReportsOfResident(@Param("nationalId") String nationalId);

    boolean existsByServiceRequest(ServiceRequest serviceRequest);

}

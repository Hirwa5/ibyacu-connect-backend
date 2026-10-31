package com.auca.ibyacu_connect.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auca.ibyacu_connect.model.Artisan;
import com.auca.ibyacu_connect.model.RequestStatus;
import com.auca.ibyacu_connect.model.ServiceRequest;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, UUID> {

    List<ServiceRequest> findByStatusOrderByRequestDateAsc(RequestStatus status);

    List<ServiceRequest> findByRequestDateBetweenOrderByRequestDateAsc(LocalDate start, LocalDate end);

    boolean existsByArtisanAndRequestDateAndStatusNot(Artisan artisan, LocalDate requestDate, RequestStatus status);

    @Query("SELECT s.status, COUNT(s) FROM ServiceRequest s GROUP BY s.status")
    List<Object[]> countServiceRequestsPerStatus();

    @Modifying
    @Query("UPDATE ServiceRequest s SET s.status = com.auca.ibyacu_connect.model.RequestStatus.DECLINED "
            + "WHERE s.artisan.id = :artisanId AND s.requestDate = :requestDate "
            + "AND s.status <> com.auca.ibyacu_connect.model.RequestStatus.FINISHED")
    int cancelDay(@Param("artisanId") UUID artisanId, @Param("requestDate") LocalDate requestDate);

    long countByStatus(RequestStatus status);

    long countByRequestDate(LocalDate requestDate);

}

package com.auca.ibyacu_connect.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.ibyacu_connect.model.RequestStatus;
import com.auca.ibyacu_connect.repository.ArtisanRepository;
import com.auca.ibyacu_connect.repository.ResidentRepository;
import com.auca.ibyacu_connect.repository.ServiceRequestRepository;
import com.auca.ibyacu_connect.repository.WorkReportRepository;
import com.auca.ibyacu_connect.repository.WorkshopRepository;

@Service
public class DashboardService {

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private ArtisanRepository artisanRepository;

    @Autowired
    private WorkshopRepository workshopRepository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private WorkReportRepository workReportRepository;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("residents", residentRepository.count());
        summary.put("artisans", artisanRepository.count());
        summary.put("freeWorkshops", (long) workshopRepository.findFreeWorkshops().size());
        summary.put("requestsToday", serviceRequestRepository.countByRequestDate(LocalDate.now()));
        summary.put("open", serviceRequestRepository.countByStatus(RequestStatus.OPEN));
        summary.put("workReports", workReportRepository.count());
        return summary;
    }
}

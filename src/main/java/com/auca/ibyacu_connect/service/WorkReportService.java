package com.auca.ibyacu_connect.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.ibyacu_connect.model.RequestStatus;
import com.auca.ibyacu_connect.model.ServiceRequest;
import com.auca.ibyacu_connect.model.WorkReport;
import com.auca.ibyacu_connect.repository.ServiceRequestRepository;
import com.auca.ibyacu_connect.repository.WorkReportRepository;

@Service
public class WorkReportService {

    @Autowired
    private WorkReportRepository workReportRepository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public String saveWorkReport(UUID serviceRequestId, WorkReport workReport) {
        Optional<ServiceRequest> found = serviceRequestRepository.findById(serviceRequestId);
        if (found.isEmpty()) {
            return "The service request with that id does not exist";
        }
        ServiceRequest serviceRequest = found.get();
        if (serviceRequest.getStatus() != RequestStatus.FINISHED) {
            return "Work reports are only for FINISHED service requests";
        }
        workReport.setReportDate(LocalDate.now());
        workReport.setServiceRequest(serviceRequest);
        workReportRepository.save(workReport);
        return "Work report saved successfully";
    }

    public List<WorkReport> getReportsOfResident(String nationalId) {
        return workReportRepository.findReportsOfResident(nationalId);
    }

    public String deleteWorkReport(UUID id) {
        if (!workReportRepository.existsById(id)) {
            return "The work report with that id does not exist";
        }
        workReportRepository.deleteById(id);
        return "Work report deleted successfully";
    }
}

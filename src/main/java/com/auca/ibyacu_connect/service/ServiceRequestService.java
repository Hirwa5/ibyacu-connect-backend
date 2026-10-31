package com.auca.ibyacu_connect.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.ibyacu_connect.model.RequestStatus;
import com.auca.ibyacu_connect.model.ServiceRequest;
import com.auca.ibyacu_connect.repository.ServiceRequestRepository;
import com.auca.ibyacu_connect.repository.WorkReportRepository;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private WorkReportRepository workReportRepository;

    public String saveServiceRequest(ServiceRequest serviceRequest) {
        if (serviceRequest.getRequestDate().isBefore(LocalDate.now())) {
            return "Service request date cannot be in the past";
        }
        boolean booked = serviceRequestRepository.existsByArtisanAndRequestDateAndStatusNot(
                serviceRequest.getArtisan(), serviceRequest.getRequestDate(), RequestStatus.DECLINED);
        if (booked) {
            return "Artisan is already booked on that date";
        }
        serviceRequest.setStatus(RequestStatus.OPEN);
        serviceRequestRepository.save(serviceRequest);
        return "Service request saved successfully";
    }

    public List<ServiceRequest> getAllServiceRequests() {
        return serviceRequestRepository.findAll();
    }

    public Optional<ServiceRequest> getServiceRequestById(UUID id) {
        return serviceRequestRepository.findById(id);
    }

    public String updateServiceRequest(UUID id, ServiceRequest serviceRequest) {
        Optional<ServiceRequest> existing = serviceRequestRepository.findById(id);
        if (existing.isEmpty()) {
            return "The service request with that id does not exist";
        }
        ServiceRequest savedRequest = existing.get();
        savedRequest.setRequestDate(serviceRequest.getRequestDate());
        savedRequest.setDescription(serviceRequest.getDescription());
        savedRequest.setResident(serviceRequest.getResident());
        savedRequest.setArtisan(serviceRequest.getArtisan());
        serviceRequestRepository.save(savedRequest);
        return "Service request updated successfully";
    }

    public String deleteServiceRequest(UUID id) {
        Optional<ServiceRequest> existing = serviceRequestRepository.findById(id);
        if (existing.isEmpty()) {
            return "The service request with that id does not exist";
        }
        if (workReportRepository.existsByServiceRequest(existing.get())) {
            return "That service request has work reports, delete them first";
        }
        serviceRequestRepository.deleteById(id);
        return "Service request deleted successfully";
    }

    public List<ServiceRequest> getServiceRequestsByStatus(RequestStatus status) {
        return serviceRequestRepository.findByStatusOrderByRequestDateAsc(status);
    }

    public List<ServiceRequest> getServiceRequestsBetween(LocalDate start, LocalDate end) {
        return serviceRequestRepository.findByRequestDateBetweenOrderByRequestDateAsc(start, end);
    }

    public String updateStatus(UUID id, RequestStatus newStatus) {
        Optional<ServiceRequest> existing = serviceRequestRepository.findById(id);
        if (existing.isEmpty()) {
            return "The service request with that id does not exist";
        }
        ServiceRequest serviceRequest = existing.get();
        RequestStatus currentStatus = serviceRequest.getStatus();
        boolean allowed = false;
        if (currentStatus == RequestStatus.OPEN) {
            if (newStatus == RequestStatus.ACCEPTED || newStatus == RequestStatus.DECLINED) {
                allowed = true;
            }
        } else if (currentStatus == RequestStatus.ACCEPTED) {
            if (newStatus == RequestStatus.FINISHED || newStatus == RequestStatus.DECLINED) {
                allowed = true;
            }
        }
        if (!allowed) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }
        serviceRequest.setStatus(newStatus);
        serviceRequestRepository.save(serviceRequest);
        return "Service request status updated successfully";
    }
}

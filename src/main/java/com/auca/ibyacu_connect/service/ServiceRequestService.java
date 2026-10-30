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

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public String saveServiceRequest(ServiceRequest serviceRequest) {
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
        savedRequest.setStatus(serviceRequest.getStatus());
        savedRequest.setDescription(serviceRequest.getDescription());
        savedRequest.setResident(serviceRequest.getResident());
        savedRequest.setArtisan(serviceRequest.getArtisan());
        serviceRequestRepository.save(savedRequest);
        return "Service request updated successfully";
    }

    public String deleteServiceRequest(UUID id) {
        if (!serviceRequestRepository.existsById(id)) {
            return "The service request with that id does not exist";
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
}

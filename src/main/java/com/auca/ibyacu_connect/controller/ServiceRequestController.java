package com.auca.ibyacu_connect.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auca.ibyacu_connect.model.RequestStatus;
import com.auca.ibyacu_connect.model.ServiceRequest;
import com.auca.ibyacu_connect.service.ServiceRequestService;

@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService serviceRequestService;

    @PostMapping("/save")
    public ResponseEntity<String> saveServiceRequest(@RequestBody ServiceRequest serviceRequest) {
        String message = serviceRequestService.saveServiceRequest(serviceRequest);
        if (message.equals("Service request date cannot be in the past")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if (message.equals("Artisan is already booked on that date")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServiceRequest>> getAllServiceRequests() {
        return new ResponseEntity<>(serviceRequestService.getAllServiceRequests(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getServiceRequestById(@PathVariable UUID id) {
        Optional<ServiceRequest> serviceRequest = serviceRequestService.getServiceRequestById(id);
        if (serviceRequest.isPresent()) {
            return new ResponseEntity<>(serviceRequest.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The service request with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateServiceRequest(@PathVariable UUID id, @RequestBody ServiceRequest serviceRequest) {
        String message = serviceRequestService.updateServiceRequest(id, serviceRequest);
        if (message.equals("The service request with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteServiceRequest(@PathVariable UUID id) {
        String message = serviceRequestService.deleteServiceRequest(id);
        if (message.equals("The service request with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.equals("That service request has work reports, delete them first")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<ServiceRequest>> getServiceRequestsByStatus(@RequestParam RequestStatus status) {
        return new ResponseEntity<>(serviceRequestService.getServiceRequestsByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<List<ServiceRequest>> getServiceRequestsBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(serviceRequestService.getServiceRequestsBetween(startDate, endDate), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam RequestStatus status) {
        String message = serviceRequestService.updateStatus(id, status);
        if (message.equals("The service request with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.startsWith("Cannot change status from")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

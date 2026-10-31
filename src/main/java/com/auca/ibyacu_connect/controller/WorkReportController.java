package com.auca.ibyacu_connect.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auca.ibyacu_connect.model.WorkReport;
import com.auca.ibyacu_connect.service.WorkReportService;

@RestController
@RequestMapping("/api/work-reports")
public class WorkReportController {

    @Autowired
    private WorkReportService workReportService;

    @PostMapping("/save")
    public ResponseEntity<String> saveWorkReport(@RequestParam UUID serviceRequestId, @RequestBody WorkReport workReport) {
        String message = workReportService.saveWorkReport(serviceRequestId, workReport);
        if (message.equals("The service request with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.equals("Work reports are only for FINISHED service requests")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/resident/{nationalId}")
    public ResponseEntity<List<WorkReport>> getReportsOfResident(@PathVariable String nationalId) {
        return new ResponseEntity<>(workReportService.getReportsOfResident(nationalId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWorkReport(@PathVariable UUID id) {
        String message = workReportService.deleteWorkReport(id);
        if (message.equals("The work report with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

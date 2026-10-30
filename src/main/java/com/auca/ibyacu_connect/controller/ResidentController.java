package com.auca.ibyacu_connect.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auca.ibyacu_connect.model.Resident;
import com.auca.ibyacu_connect.service.ResidentService;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    @PostMapping("/save")
    public ResponseEntity<String> saveResident(@RequestBody Resident resident) {
        String message = residentService.saveResident(resident);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Resident>> getAllResidents() {
        return new ResponseEntity<>(residentService.getAllResidents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResidentById(@PathVariable UUID id) {
        Optional<Resident> resident = residentService.getResidentById(id);
        if (resident.isPresent()) {
            return new ResponseEntity<>(resident.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The resident with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateResident(@PathVariable UUID id, @RequestBody Resident resident) {
        String message = residentService.updateResident(id, resident);
        if (message.equals("The resident with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteResident(@PathVariable UUID id) {
        String message = residentService.deleteResident(id);
        if (message.equals("The resident with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

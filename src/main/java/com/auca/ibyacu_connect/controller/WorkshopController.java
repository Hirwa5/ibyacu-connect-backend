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

import com.auca.ibyacu_connect.model.Workshop;
import com.auca.ibyacu_connect.service.WorkshopService;

@RestController
@RequestMapping("/api/workshops")
public class WorkshopController {

    @Autowired
    private WorkshopService workshopService;

    @PostMapping("/save")
    public ResponseEntity<String> saveWorkshop(@RequestBody Workshop workshop) {
        String message = workshopService.saveWorkshop(workshop);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Workshop>> getAllWorkshops() {
        return new ResponseEntity<>(workshopService.getAllWorkshops(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkshopById(@PathVariable UUID id) {
        Optional<Workshop> workshop = workshopService.getWorkshopById(id);
        if (workshop.isPresent()) {
            return new ResponseEntity<>(workshop.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The workshop with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWorkshop(@PathVariable UUID id, @RequestBody Workshop workshop) {
        String message = workshopService.updateWorkshop(id, workshop);
        if (message.equals("The workshop with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWorkshop(@PathVariable UUID id) {
        String message = workshopService.deleteWorkshop(id);
        if (message.equals("The workshop with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

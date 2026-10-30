package com.auca.ibyacu_connect.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.ibyacu_connect.model.Workshop;
import com.auca.ibyacu_connect.repository.WorkshopRepository;

@Service
public class WorkshopService {

    @Autowired
    private WorkshopRepository workshopRepository;

    public String saveWorkshop(Workshop workshop) {
        workshopRepository.save(workshop);
        return "Workshop saved successfully";
    }

    public List<Workshop> getAllWorkshops() {
        return workshopRepository.findAll();
    }

    public Optional<Workshop> getWorkshopById(UUID id) {
        return workshopRepository.findById(id);
    }

    public String updateWorkshop(UUID id, Workshop workshop) {
        Optional<Workshop> existing = workshopRepository.findById(id);
        if (existing.isEmpty()) {
            return "The workshop with that id does not exist";
        }
        Workshop savedWorkshop = existing.get();
        savedWorkshop.setWorkshopNumber(workshop.getWorkshopNumber());
        savedWorkshop.setName(workshop.getName());
        workshopRepository.save(savedWorkshop);
        return "Workshop updated successfully";
    }

    public String deleteWorkshop(UUID id) {
        if (!workshopRepository.existsById(id)) {
            return "The workshop with that id does not exist";
        }
        workshopRepository.deleteById(id);
        return "Workshop deleted successfully";
    }

    public List<Workshop> getFreeWorkshops() {
        return workshopRepository.findFreeWorkshops();
    }
}

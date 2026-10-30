package com.auca.ibyacu_connect.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.ibyacu_connect.model.Resident;
import com.auca.ibyacu_connect.repository.ResidentRepository;

@Service
public class ResidentService {

    @Autowired
    private ResidentRepository residentRepository;

    public String saveResident(Resident resident) {
        residentRepository.save(resident);
        return "Resident saved successfully";
    }

    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    public Optional<Resident> getResidentById(UUID id) {
        return residentRepository.findById(id);
    }

    public String updateResident(UUID id, Resident resident) {
        Optional<Resident> existing = residentRepository.findById(id);
        if (existing.isEmpty()) {
            return "The resident with that id does not exist";
        }
        Resident savedResident = existing.get();
        savedResident.setFirstName(resident.getFirstName());
        savedResident.setLastName(resident.getLastName());
        savedResident.setNationalId(resident.getNationalId());
        savedResident.setVillage(resident.getVillage());
        residentRepository.save(savedResident);
        return "Resident updated successfully";
    }

    public String deleteResident(UUID id) {
        if (!residentRepository.existsById(id)) {
            return "The resident with that id does not exist";
        }
        residentRepository.deleteById(id);
        return "Resident deleted successfully";
    }

    public List<Resident> getResidentsByLastName(String lastName) {
        return residentRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public List<Resident> getResidentsOfArtisan(UUID artisanId) {
        return residentRepository.findResidentsOfArtisan(artisanId);
    }
}

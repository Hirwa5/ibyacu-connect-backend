package com.auca.ibyacu_connect.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.ibyacu_connect.model.Artisan;
import com.auca.ibyacu_connect.repository.ArtisanRepository;

@Service
public class ArtisanService {

    @Autowired
    private ArtisanRepository artisanRepository;

    public String saveArtisan(Artisan artisan) {
        artisanRepository.save(artisan);
        return "Artisan saved successfully";
    }

    public List<Artisan> getAllArtisans() {
        return artisanRepository.findAll();
    }

    public Optional<Artisan> getArtisanById(UUID id) {
        return artisanRepository.findById(id);
    }

    public String updateArtisan(UUID id, Artisan artisan) {
        Optional<Artisan> existing = artisanRepository.findById(id);
        if (existing.isEmpty()) {
            return "The artisan with that id does not exist";
        }
        Artisan savedArtisan = existing.get();
        savedArtisan.setFirstName(artisan.getFirstName());
        savedArtisan.setLastName(artisan.getLastName());
        savedArtisan.setPhone(artisan.getPhone());
        artisanRepository.save(savedArtisan);
        return "Artisan updated successfully";
    }

    public String deleteArtisan(UUID id) {
        if (!artisanRepository.existsById(id)) {
            return "The artisan with that id does not exist";
        }
        artisanRepository.deleteById(id);
        return "Artisan deleted successfully";
    }

    public List<Artisan> getArtisansByTrade(String name) {
        return artisanRepository.findArtisansByTrade(name);
    }
}

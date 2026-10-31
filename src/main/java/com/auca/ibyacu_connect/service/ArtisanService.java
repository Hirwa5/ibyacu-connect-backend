package com.auca.ibyacu_connect.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.ibyacu_connect.model.Artisan;
import com.auca.ibyacu_connect.model.Trade;
import com.auca.ibyacu_connect.model.Workshop;
import com.auca.ibyacu_connect.repository.ArtisanRepository;
import com.auca.ibyacu_connect.repository.TradeRepository;
import com.auca.ibyacu_connect.repository.WorkshopRepository;

@Service
public class ArtisanService {

    @Autowired
    private ArtisanRepository artisanRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private WorkshopRepository workshopRepository;

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

    public String addTradeToArtisan(UUID artisanId, UUID tradeId) {
        Optional<Artisan> foundArtisan = artisanRepository.findById(artisanId);
        if (foundArtisan.isEmpty()) {
            return "The artisan with that id does not exist";
        }
        Optional<Trade> foundTrade = tradeRepository.findById(tradeId);
        if (foundTrade.isEmpty()) {
            return "That trade does not exist";
        }
        Artisan artisan = foundArtisan.get();
        Trade trade = foundTrade.get();
        if (artisan.getTrades().contains(trade)) {
            return "The artisan already has that trade";
        }
        artisan.getTrades().add(trade);
        artisanRepository.save(artisan);
        return "Trade added to the artisan";
    }

    public String changeWorkshop(UUID artisanId, int workshopNumber) {
        Optional<Artisan> foundArtisan = artisanRepository.findById(artisanId);
        if (foundArtisan.isEmpty()) {
            return "The artisan with that id does not exist";
        }
        Optional<Workshop> foundWorkshop = workshopRepository.findByWorkshopNumber(workshopNumber);
        if (foundWorkshop.isEmpty()) {
            return "The workshop with that workshopNumber does not exist";
        }
        Workshop workshop = foundWorkshop.get();
        if (artisanRepository.existsByWorkshop(workshop)) {
            return "That workshop is taken by another artisan";
        }
        Artisan artisan = foundArtisan.get();
        artisan.setWorkshop(workshop);
        artisanRepository.save(artisan);
        return "Workshop changed successfully";
    }
}

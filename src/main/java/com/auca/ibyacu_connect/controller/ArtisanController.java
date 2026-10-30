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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auca.ibyacu_connect.model.Artisan;
import com.auca.ibyacu_connect.service.ArtisanService;

@RestController
@RequestMapping("/api/artisans")
public class ArtisanController {

    @Autowired
    private ArtisanService artisanService;

    @PostMapping("/save")
    public ResponseEntity<String> saveArtisan(@RequestBody Artisan artisan) {
        String message = artisanService.saveArtisan(artisan);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Artisan>> getAllArtisans() {
        return new ResponseEntity<>(artisanService.getAllArtisans(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtisanById(@PathVariable UUID id) {
        Optional<Artisan> artisan = artisanService.getArtisanById(id);
        if (artisan.isPresent()) {
            return new ResponseEntity<>(artisan.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The artisan with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateArtisan(@PathVariable UUID id, @RequestBody Artisan artisan) {
        String message = artisanService.updateArtisan(id, artisan);
        if (message.equals("The artisan with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteArtisan(@PathVariable UUID id) {
        String message = artisanService.deleteArtisan(id);
        if (message.equals("The artisan with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/by-trade")
    public ResponseEntity<List<Artisan>> getArtisansByTrade(@RequestParam String name) {
        return new ResponseEntity<>(artisanService.getArtisansByTrade(name), HttpStatus.OK);
    }
}

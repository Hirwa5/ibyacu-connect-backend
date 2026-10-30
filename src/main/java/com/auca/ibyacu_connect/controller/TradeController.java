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

import com.auca.ibyacu_connect.model.Trade;
import com.auca.ibyacu_connect.service.TradeService;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping("/save")
    public ResponseEntity<String> saveTrade(@RequestBody Trade trade) {
        String message = tradeService.saveTrade(trade);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Trade>> getAllTrades() {
        return new ResponseEntity<>(tradeService.getAllTrades(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTradeById(@PathVariable UUID id) {
        Optional<Trade> trade = tradeService.getTradeById(id);
        if (trade.isPresent()) {
            return new ResponseEntity<>(trade.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The trade with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTrade(@PathVariable UUID id, @RequestBody Trade trade) {
        String message = tradeService.updateTrade(id, trade);
        if (message.equals("The trade with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTrade(@PathVariable UUID id) {
        String message = tradeService.deleteTrade(id);
        if (message.equals("The trade with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/unused")
    public ResponseEntity<List<Trade>> getUnusedTrades() {
        return new ResponseEntity<>(tradeService.getUnusedTrades(), HttpStatus.OK);
    }
}

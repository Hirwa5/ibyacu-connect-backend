package com.auca.ibyacu_connect.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.ibyacu_connect.model.Trade;
import com.auca.ibyacu_connect.repository.TradeRepository;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public String saveTrade(Trade trade) {
        tradeRepository.save(trade);
        return "Trade saved successfully";
    }

    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> getTradeById(UUID id) {
        return tradeRepository.findById(id);
    }

    public String updateTrade(UUID id, Trade trade) {
        Optional<Trade> existing = tradeRepository.findById(id);
        if (existing.isEmpty()) {
            return "The trade with that id does not exist";
        }
        Trade savedTrade = existing.get();
        savedTrade.setName(trade.getName());
        tradeRepository.save(savedTrade);
        return "Trade updated successfully";
    }

    public String deleteTrade(UUID id) {
        if (!tradeRepository.existsById(id)) {
            return "The trade with that id does not exist";
        }
        tradeRepository.deleteById(id);
        return "Trade deleted successfully";
    }
}

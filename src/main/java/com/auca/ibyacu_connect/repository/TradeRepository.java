package com.auca.ibyacu_connect.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.auca.ibyacu_connect.model.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, UUID> {

    @Query("SELECT t FROM Trade t WHERE t.artisans IS EMPTY")
    List<Trade> findUnusedTrades();

}

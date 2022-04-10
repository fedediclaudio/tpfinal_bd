package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.HistoricalProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalProductPriceRepository extends JpaRepository<HistoricalProductPrice, Long> {
}

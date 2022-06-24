package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;

import java.time.LocalDate;
import java.util.List;

public interface HistoricalProductPriceService {
    List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(String product_id, LocalDate start_date, LocalDate finish_date);
}

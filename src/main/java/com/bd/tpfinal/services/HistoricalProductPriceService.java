package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface HistoricalProductPriceService {
    List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(long product_id, LocalDate start_date, LocalDate finish_date);
}

package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface HistoricalProductPriceService {
    List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(long product_id, LocalDateTime start_date, LocalDateTime finish_date);
}

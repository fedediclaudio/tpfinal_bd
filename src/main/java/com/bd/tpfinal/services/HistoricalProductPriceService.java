package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;

import java.util.Date;
import java.util.List;

public interface HistoricalProductPriceService {
    List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(long product_id, Date start_date, Date finish_date);
}

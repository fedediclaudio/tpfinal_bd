package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HistoricalProductPriceServiceImpl implements HistoricalProductPriceService {
    @Override
    public List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(long product_id, LocalDate start_date, LocalDate finish_date) {
        return null;
    }
}

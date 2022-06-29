package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.repositories.HistoricalProductPriceRepository;
import com.bd.tpfinal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HistoricalProductPriceServiceImpl implements HistoricalProductPriceService {
    @Autowired
    private HistoricalProductPriceRepository historicalProductPriceRepository;

    @Override
    public List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(String product_id, LocalDate start_date, LocalDate finish_date) {
        return historicalProductPriceRepository.getPricesBetweenDatesForProductId(product_id, start_date, finish_date);
    }
}

package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.repositories.HistoricalProductPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HistoricalProductPriceServiceImpl implements HistoricalProductPriceService {
    @Autowired
    private HistoricalProductPriceRepository historicalProductPriceRepository;

    @Override
    public List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(long product_id, Date start_date, Date finish_date) {
        return historicalProductPriceRepository.findByProductIdAndStartDateGreaterThanAndFinishDateLessThan(product_id, start_date, finish_date);
    }
}

package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public interface HistoricalProductPriceService
{
    public void newHistoricalProductPrice(HistoricalProductPrice newHistoricalProductPrice);
    public List<HistoricalProductPrice> getAll(Long productPriceId);
    public HistoricalProductPrice getById(Long id);
    public List<HistoricalProductPrice> getPrices(Long id_product, LocalDate desde, LocalDate hasta);
    public void saveListHistoricalProductoPrice(List<HistoricalProductPrice> newHpp);
}

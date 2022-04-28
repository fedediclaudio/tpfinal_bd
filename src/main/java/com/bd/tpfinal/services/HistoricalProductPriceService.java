package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HistoricalProductPriceService
{
    public void newHistoricalProductPrice(HistoricalProductPrice newHistoricalProductPrice);
    public List<HistoricalProductPrice> getAll(Long productPriceId);
    public HistoricalProductPrice getById(Long id);
}

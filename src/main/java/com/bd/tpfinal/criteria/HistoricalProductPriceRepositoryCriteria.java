package com.bd.tpfinal.criteria;

import com.bd.tpfinal.model.HistoricalProductPrice;

import java.time.LocalDate;
import java.util.List;

public interface HistoricalProductPriceRepositoryCriteria {
    List<HistoricalProductPrice> getPricesBetweenDatesForProductId(String productId, LocalDate startDate, LocalDate finishDate);
}

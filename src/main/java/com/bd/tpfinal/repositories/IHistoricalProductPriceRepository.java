package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.HistoricalProductPrice;

import java.util.List;

public interface IHistoricalProductPriceRepository {

    List<HistoricalProductPrice> getHistoricalPricesListOrderByStartDate(String idProduct);

}

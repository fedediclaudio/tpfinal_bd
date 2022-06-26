package com.bd.tpfinal.repositories;

import com.bd.tpfinal.criteria.HistoricalProductPriceRepositoryCriteria;
import com.bd.tpfinal.model.HistoricalProductPrice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoricalProductPriceRepository extends MongoRepository<HistoricalProductPrice, String>, HistoricalProductPriceRepositoryCriteria {
}
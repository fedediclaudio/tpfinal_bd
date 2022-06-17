package com.bd.tpfinal.repositories.implementations;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.repositories.interfaces.IHistoricalProductPriceRepository;

public interface HistoricalProductPriceRepository extends MongoRepository<HistoricalProductPrice, String>, IHistoricalProductPriceRepository {

	List<HistoricalProductPrice> findByProduct_Id(@Param("id") String id);
    List<HistoricalProductPrice> findByProduct_IdAndStartDateGreaterThanAndFinishDateLessThan(@Param("id") String id, @Param("starDate") LocalDate starDate, @Param("endDate") LocalDate endDate);
    HistoricalProductPrice findByProduct_IdAndStartDateGreaterThanAndFinishDateIsNull(@Param("id") String id, @Param("starDate") LocalDate starDate);
    List<HistoricalProductPrice> findByStartDateGreaterThanAndFinishDateLessThanOrFinishDateIsNullAndProduct_Id(@Param("starDate") LocalDate starDate, @Param("endDate") LocalDate endDate, @Param("id") String id);
    
}
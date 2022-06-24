package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.HistoricalProductPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface HistoricalProductPriceRepository extends MongoRepository<HistoricalProductPrice, String> {

    @Query("{product: $0, start_date: {$gte: ?1, $lte: ?2 }}")
    List<HistoricalProductPrice> findHistoricalProductPricesBetweenTwoDates();
    List<HistoricalProductPrice> findByStartDateGreaterThanAndFinishDateLessThanOrFinishDateIsNullAndProduct(LocalDate startDate, LocalDate finishDate, String productId);


}
/*
@Query(value = "SELECT hpp.* "
        + "FROM tpfinal.historical_product_price hpp "
        + "where hpp.id_product = :productId "
        + "AND (hpp.start_date >= :start_date and hpp.finish_date <= :finish_date)")*/

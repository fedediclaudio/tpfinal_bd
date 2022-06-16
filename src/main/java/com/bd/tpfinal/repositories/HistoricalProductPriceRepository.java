package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.HistoricalProductPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface HistoricalProductPriceRepository extends MongoRepository<HistoricalProductPrice, String> {

    @Query("{start_date: {$gte: ?0, $lte: ?1 }}")
    List<HistoricalProductPrice> findHistoricalProductPricesBetweenTwoDates(long productId, LocalDate start_date, LocalDate finish_date);


}
/*
@Query(value = "SELECT hpp.* "
        + "FROM tpfinal.historical_product_price hpp "
        + "where hpp.id_product = :productId "
        + "AND (hpp.start_date >= :start_date and hpp.finish_date <= :finish_date)")*/

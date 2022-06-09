package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.HistoricalProductPrice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface HistoricalProductPriceRepository  extends CrudRepository<HistoricalProductPrice, Long> {

    @Query(value = "SELECT hpp.* "
            + "FROM tpfinal.historical_product_price hpp "
            + "where hpp.id_product = :productId "
            + "AND (hpp.start_date >= :start_date and hpp.finish_date <= :finish_date)", nativeQuery = true)
    List<HistoricalProductPrice> findHistoricalProductPricesBetweenTwoDates(long productId, LocalDate start_date, LocalDate finish_date);
}

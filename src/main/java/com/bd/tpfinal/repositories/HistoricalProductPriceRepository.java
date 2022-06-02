package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.HistoricalProductPrice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface HistoricalProductPriceRepository  extends CrudRepository<HistoricalProductPrice, Long> {

    public List<HistoricalProductPrice> findByProductIdAndStartDateGreaterThanAndFinishDateLessThan(long productId, LocalDateTime start_date, LocalDateTime finish_date);
}

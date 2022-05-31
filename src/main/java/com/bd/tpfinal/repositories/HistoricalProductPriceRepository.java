package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.HistoricalProductPrice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistoricalProductPriceRepository  extends CrudRepository<HistoricalProductPrice, Long> {

    public List<HistoricalProductPrice> findByProductIdAndStartDateGreaterThanAndFinishDateLessThan(long productId, Date start_date, Date finish_date);
}

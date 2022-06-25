package com.bd.tpfinal.repositories.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.bd.tpfinal.model.HistoricalProductPrice;

public interface IHistoricalProductPriceRepository {

	// Los metodos que se vayan a porponer aca, tienen que estar implementados en su implementacion (HistoricalProductPriceImpl)
	
	List<HistoricalProductPrice> getHistoricalPricesListOrderByStartDate(long idProduct);
	List<HistoricalProductPrice> getHistoricalPricesBetweenTwoDates(long idProduct, LocalDate startDate, LocalDate endDate);
	
}

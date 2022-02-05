package com.bd.tpfinal.repositories.interfaces;

import java.util.List;

import com.bd.tpfinal.model.HistoricalProductPrice;

public interface IProductRepository {
	
	// Los metodos que se vayan a porponerse aca, tienen que estar implementados en su implementacion (ProductRepositoryImpl)
	
	List<HistoricalProductPrice> getHistoricalPricesListOrderByStartDate(long idProduct);
}

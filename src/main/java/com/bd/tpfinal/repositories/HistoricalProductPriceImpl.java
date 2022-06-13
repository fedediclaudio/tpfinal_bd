package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.HistoricalProductPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class HistoricalProductPriceImpl implements IHistoricalProductPriceRepository {
	@Autowired MongoTemplate mongoTemplate;

	public List<HistoricalProductPrice> getHistoricalPricesListOrderByStartDate(String idProduct) {
		Criteria criteria = Criteria.where("product.id").is(idProduct);
		
		return mongoTemplate.find(new Query(criteria), HistoricalProductPrice.class);
	}
}

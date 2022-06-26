package com.bd.tpfinal.criteria;

import com.bd.tpfinal.model.HistoricalProductPrice;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDate;
import java.util.List;

public class HistoricalProductPriceRepositoryCriteriaImpl implements HistoricalProductPriceRepositoryCriteria {

    private final MongoTemplate mongoTemplate;

    public HistoricalProductPriceRepositoryCriteriaImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<HistoricalProductPrice> getPricesBetweenDatesForProductId(String productId, LocalDate startDate, LocalDate finishDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("product.$id").is(new ObjectId(productId)).and("startDate").gt(startDate).and("finishDate").lt(finishDate));
        return mongoTemplate.find(query, HistoricalProductPrice.class);
    }
}

package com.bd.tpfinal.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.bd.tpfinal.dto.ProductTypePriceDTO;
import com.bd.tpfinal.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, ObjectId>{
	
	 public List<Product> findBySupplierId(ObjectId id); //getAllProductsAndTypesForSupplier
	 
	 public Optional<Product> findByNameAndSupplier(String nameProducto, ObjectId supplierId);
	
	@Aggregation(pipeline = {"{'$lookup':{ from: 'productType', localField: 'types.$id', foreignField: '_id', as: 'output' }, }}",
            "  {'$unwind': '$output'}",
            "  {'$group':{_id:'$output._id',  name: { $first: '$output.name' }, priceProm:{$avg: '$price'}, }}}"})
	public List<ProductTypePriceDTO> getPricesAvgForProducts(); //for getPricesAvgForProducts
	
	
	@Query("{$and: [ {$or:[{'prices.finishDate': {$gte: ?0}}, {'prices.finishDate': {$eq: ISODate ('2200-12-31')}}]}, "
			+ "{'prices.startDate':{$lte: ?1}}, {'_id':{$eq: ObjectId(?2)}}]}")
	public Product getPricesBetweenDates(LocalDate startDate, LocalDate finishDate,  ObjectId id);
}
package com.bd.tpfinal.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bd.tpfinal.dto.OrderItemsDTO;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Product;


@Repository
public interface OrderRepository extends MongoRepository<Order, ObjectId>{
		
	public Optional<Order> findFirstByDateOfOrderEqualsOrderByTotalPriceDesc(LocalDate fecha); //for getMayorPrecioTotalForDay
	
	public List<Order> findByItems_Product(Product p);
	
	@Aggregation(pipeline = {"{'$lookup':{ from: 'product', localField: 'items.product.$id', foreignField: '_id', as: 'output' }, }}",
            "  {'$unwind': '$output'}", 
            "  {'$match':  {'output.supplier.$id' :ObjectId(?0)}}",
            "  {'$addFields':{'numberOfItems' : { $size : '$items' }}}", 
            "  {'$project':{'_id' : 1, 'numberOfItems':1}}", 
            "  {'$sort' : { 'numberOfItems' : -1 } }","{ '$limit' : 1 }"})
	public OrderItemsDTO getOrderMasProductsForSupplier (ObjectId supplierId);
	
	
}

	          
	          
	            

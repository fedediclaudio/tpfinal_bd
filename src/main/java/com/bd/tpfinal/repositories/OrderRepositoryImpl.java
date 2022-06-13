package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.interfaces.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryImpl implements IOrderRepository {
    @Autowired MongoTemplate mongoTemplate;
    
	private Criteria getBasicOrderForClientFilter(String idClient, String status) {
		Criteria criteria = Criteria.where("client.id").is(idClient);
		
		if (status != null)		
			criteria.andOperator( Criteria.where("status.name").is(status) );
		
		return criteria;
	}
	
	private Criteria getBasicOrderForDeliveryManFilter(String idDeliveryMan, String status) {
		Criteria criteria = Criteria.where("deliveryMan.id").is(idDeliveryMan);
		
		if (status != null)		
			criteria.andOperator( Criteria.where("status.name").is(status) );
		
		return criteria;
	}
	
	@Override
	public List<Order> getAllOrdersForClient(String idClient, String status) {
		Criteria criteria = getBasicOrderForClientFilter(idClient, status);
		
		return mongoTemplate.find(new Query(criteria), Order.class);
	}

	@Override
	public List<Order> getAllOrdersForDeliveryMan(String idDeliveryMan, String status) {
		Criteria criteria = getBasicOrderForDeliveryManFilter(idDeliveryMan, status);

		return mongoTemplate.find(new Query(criteria), Order.class);
	}
	
	@Override
	public Order getNextOrderForClient(String idClient, String status) {
		Criteria criteria = getBasicOrderForClientFilter(idClient, status);
		
		List<Order> orders = mongoTemplate.find(new Query(criteria), Order.class);
		
		try {
			return orders.get(0);
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	public Order getNextOrderDeliveryMan(String idDeliveryMan, String status) {
		Criteria criteria = getBasicOrderForDeliveryManFilter(idDeliveryMan, status);
		List<Order> orders = mongoTemplate.find(new Query(criteria), Order.class);
		
		try {
			return orders.get(0);
		}
		catch (Exception e) {
			return null;
		}
	}
}

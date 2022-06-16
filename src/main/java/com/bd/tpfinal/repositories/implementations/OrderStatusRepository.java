package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bd.tpfinal.model.OrderStatus;

public interface OrderStatusRepository extends MongoRepository<OrderStatus, String> {
	

	OrderStatus getOrderStatusById(String id);
	
	OrderStatus getOrderStatusByName(String name);

}

package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bd.tpfinal.model.OrderStatus;
import com.bd.tpfinal.repositories.interfaces.IOrderStatusRepository;

public interface OrderStatusRepository extends MongoRepository<OrderStatus, String>, IOrderStatusRepository {
	
	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	OrderStatus getOrderStatusById(String id);
	
	OrderStatus getOrderStatusByName(String name);

}

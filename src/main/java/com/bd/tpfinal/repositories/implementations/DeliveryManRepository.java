package com.bd.tpfinal.repositories.implementations;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.repositories.interfaces.IDeliveryManRepository;

public interface DeliveryManRepository extends MongoRepository<DeliveryMan, Long>, IDeliveryManRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	DeliveryMan getDeliveryManById(String id);
	
	@Query("{'free' : true , 'active' : true}")
	List<DeliveryMan> getFreeDeliveryManList();

	List<DeliveryMan> findTop10ByOrderByScoreDesc();
}

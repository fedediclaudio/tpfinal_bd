package com.bd.tpfinal.repositories.implementations;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.bd.tpfinal.model.DeliveryMan;

public interface DeliveryManRepository extends MongoRepository<DeliveryMan, String> {

	@Query("{'free' : true , 'active' : true}")
	List<DeliveryMan> getFreeDeliveryManList();

	List<DeliveryMan> findTop10ByOrderByScoreDesc();
}

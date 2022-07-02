package com.bd.tpfinal.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bd.tpfinal.model.DeliveryMan;

@Repository
public interface DeliveryManRepository extends MongoRepository<DeliveryMan, ObjectId>{
	
	public Optional<DeliveryMan> findByUsername(String aUsername);

    public List<DeliveryMan> findByFreeTrueAndActiveTrue();
    
    public List<DeliveryMan> findFirst10ByActiveTrueOrderByScoreDesc(); //for getDiezMayorScore
}

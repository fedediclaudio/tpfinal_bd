package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.DeliveryMan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeliveryManRepository extends MongoRepository<DeliveryMan, String> {
}

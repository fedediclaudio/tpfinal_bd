package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.DeliveryMan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DeliveryManRepository extends MongoRepository<DeliveryMan, String> {

    @Query("{'free' : true , 'active' : true}")
    List<DeliveryMan> getFreeDeliveryManList();
}

package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.DeliveryMan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface DeliveryManRepository extends MongoRepository<DeliveryMan, String> {
    Optional<DeliveryMan> findTopByFree(Boolean isFree);

    List<DeliveryMan> findFirst10ByOrderByNumberOfSuccessOrdersDesc();
}

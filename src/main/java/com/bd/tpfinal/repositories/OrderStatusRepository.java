package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface OrderStatusRepository extends MongoRepository<OrderStatus, String> {
}

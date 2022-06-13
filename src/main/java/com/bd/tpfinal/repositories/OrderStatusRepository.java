package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderStatusRepository extends MongoRepository<OrderStatus, String> {


    OrderStatus getOrderStatusById(String id);

    OrderStatus getOrderStatusByName(String name);

}

package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}

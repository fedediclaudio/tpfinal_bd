package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>
{
}

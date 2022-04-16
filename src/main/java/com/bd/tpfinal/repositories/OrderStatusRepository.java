package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long>
{
    @Query(value = "SELECT oe FROM OrderStatus oe WHERE oe.order.id = :id_order")
    OrderStatus findByOrder(@Param("id_order") Long id_order);
}

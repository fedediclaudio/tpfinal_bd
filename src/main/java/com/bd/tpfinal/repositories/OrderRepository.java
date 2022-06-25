package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    /*@Query(value = "")
    List<Order> getOrdersConMasProductosDeSupplier(long supplier_id);

    @Query(value= "")
    Optional<Order> getOrdenConMayorPrecioDelDia(LocalDate fecha);

    @Query(value= "")
    List<Order> findOrdersConMasProductosDeSupplier(long supplier_id);


    @Query(value = "")
    Optional<Order> getOrdenConMayorPrecioTotalDelDia(LocalDate fecha);

    @Query(value = "")
    Optional<Order> findOrderWithItemId(Long id);

    @Query(value= "")
    List<Order> findOrdersDeSupplier(long supplier_id);
*/
}

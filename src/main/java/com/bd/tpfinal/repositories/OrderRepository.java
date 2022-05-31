package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    @Query(value = "SELECT o.* FROM orders o, ( "
            + "SELECT id_order, SUM(quantity) AS cant FROM item  i "
            + "INNER JOIN product p on p.id = i.id_product "
            + "WHERE p.supplier_id = ?1 "
            + "GROUP BY id_order ) sq WHERE o.number = sq.id_order "
            + "ORDER BY cant DESC LIMIT 10;", nativeQuery = true)
    public List<Order> getOrdersConMasProductosDeSupplier(long supplier_id);

    @Query(value="SELECT *, MAX(total_price) "
            + "from orders "
            + "where date (date_of_order) = :fecha", nativeQuery = true)
    public Optional<Order> getOrdenConMayorPrecioDelDia(@Param("fecha") @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate fecha);
}
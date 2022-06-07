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
    List<Order> getOrdersConMasProductosDeSupplier(long supplier_id);

    @Query(value= "SELECT o.*"
            + "from orders o "
            + "where o.date_of_order = :fecha "
            + "order by o.total_price desc "
            + "limit 1",
            nativeQuery = true)
    Optional<Order> getOrdenConMayorPrecioDelDia(LocalDate fecha);

    @Query(value= "select o.*, ordenes.cantidad as CantidadProductos "
            + "from orders o inner join "
            + "(select o.id, sum(i.quantity) as cantidad "
            + "from orders o inner join item i on o.id =  i.id_order "
            + "inner join product p on i.id_product = p.id "
            + "where p.supplier_id = :supplier_id "
            + "group by o.id) Ordenes on o.id = ordenes.id", nativeQuery = true)
    List<Order> findOrdersConMasProductosDeSupplier(long supplier_id);

    @Query(value="SELECT * from supplier", nativeQuery = true)
    Order getOrdenConMayorPrecioTotalDelDia(LocalDate fecha);
}
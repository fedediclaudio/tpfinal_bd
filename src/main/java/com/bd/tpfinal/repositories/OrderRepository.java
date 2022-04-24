package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus_Name(String status);

    Optional<Order> findOneByNumber(int number);

    @Query(nativeQuery = true,
            value = "SELECT * FROM orders o WHERE o.date_of_order BETWEEN :from AND :to ORDER BY o.total_price DESC LIMIT 1")
    Order findMaxTotalPriceBetweenDates(@Param("from") String from, @Param("to")  String to);

    @Query(nativeQuery = true,
            value =
    "SELECT o.* FROM orders o WHERE id = (SELECT order_id FROM ( " +
            "SELECT order_id, product_id, s.id, COUNT(s.id) cant " +
            "FROM items i " +
            "JOIN products p ON (i.product_id = p.id) " +
            "JOIN suppliers s ON (s.id = p.supplier_id) " +
            "WHERE s.id=:supplier_id GROUP BY s.id, order_id ORDER BY cant DESC LIMIT 1) selected_order)")
    Order findOrderWithMaxProductsBySupplier(@Param("supplier_id") Long supplierId);

    List<Order> findByStatus_nameAndItems_product_supplier(String status, Supplier supplier);

    Set<Order> findByStatus_nameAndQualificationIsNotNullAndItems_product_supplier(String delivered, Supplier supplier);

    @Query("FROM Order o LEFT JOIN o.items i WHERE o.id = :id")
    Optional<Order> findByIdWithItems(@Param("id") Long id);
}

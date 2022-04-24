package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySupplier_idAndActive(Long supplierId, boolean active);
    List<Product> findAllByActive(boolean active);

    @Query(nativeQuery = true, value = "select p.id, active, p.description, p.name, weight, supplier_id, type_id, avg(p.price) as price, t.* " +
            "from products p inner join product_types t on (p.type_id = t.id) group by t.id")
    List<Product> findAveragePriceByProductType();

    @Query("FROM Product p LEFT JOIN FETCH p.prices p2 where p.id = :id AND p2.startDate >= :from AND (p2.finishDate <= :to or p2.finishDate is null)")
    Optional<Product> findByIdWithPricesBetweenDates(@Param("id") Long id, @Param("from") Date fromDate, @Param("to") Date toDate);
}

package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySupplier_idAndActive(Long supplierId, boolean active);
    List<Product> findAllByActive(boolean active);

    @Query(nativeQuery = true, value = "select p.id, active, p.description, p.name, weight, supplier_id, type_id, avg(p.price) as price, t.* " +
            "from products p inner join product_types t on (p.type_id = t.id) group by t.id")
    List<Product> findAveragePriceByProductType();
}

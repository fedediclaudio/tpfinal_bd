package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findBySupplier_idAndActive(String supplierId, boolean active);
    List<Product> findAllByActive(boolean active);

//    @Query(nativeQuery = true, value = "select p.id, active, p.description, p.name, weight, supplier_id, type_id, avg(p.price) as price, t.* " +
//            "from products p inner join product_types t on (p.type_id = t.id) group by t.id")
//    List<Product> findAveragePriceByProductType();

//    @Query("FROM Product p LEFT JOIN FETCH p.prices p2 where p.id = :id AND p2.startDate >= :from AND (p2.finishDate <= :to or p2.finishDate is null)")
    Optional<Product> findByPrices_startDateGreaterThanAndPrices_finishDateLessThan(@Param("id") String id, @Param("from") Date fromDate, @Param("to") Date toDate);
}

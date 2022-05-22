package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.SupplierWithOrdersCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface SupplierWithOrdersCountRepository extends JpaRepository<SupplierWithOrdersCount, Long> {
 /*   @Query("SELECT " +
            "new com.bd.tpfinal.model.SupplierWithOrdersCount(s.id, s.name, s.cuil, s.address, s.coords, s.qualificationOfUsers, s.type,  count(o.id) AS counter) " +
            "FROM Order o JOIN o.items i JOIN i.product p JOIN p.supplier s GROUP BY s.id  ORDER BY counter DESC")
*/
    @Query(value = " select id, name, cuil, address, coords, qualificationOfUsers, typeId, type, count(*) counter from  ( " +
            "    select s.id, s.name, s.cuil, s.address, s.coords, s.qualification_of_users as qualificationOfUsers, " +
            "s.type_id as typeId, t.name as type,  count(o.id) AS count " +
            "from orders o " +
            "inner join items i on(o.id = i.order_id)  " +
            "inner join  products p on (i.product_id = p.id) " +
            "inner join suppliers s on(s.id=p.supplier_id) " +
            "inner join product_types t on(t.id=p.type_id) " +
            "group by o.id, s.id) tabla group by name ORDER BY counter DESC limit 10", nativeQuery = true)
    List<Map<String, Object>> suppliersAtLeast10Orders();

}

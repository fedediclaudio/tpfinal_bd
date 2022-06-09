package com.bd.tpfinal.repositories;

import com.bd.tpfinal.DTOs.SupplierDTO;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.SupplierType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    List<Supplier> findByNameIgnoreCaseContaining(String supplierName);

    @Query(value= "select s.* "
            + "from product P inner join item i on P.id = i.id_product "
            + "inner join orders o on i.id_order = o.id "
            + "inner join order_status os on o.id = os.id_order "
            + "inner join supplier s on p.supplier_id = s.id "
            + "where os.state = 'Delivered' "
            + "group by p.supplier_id "
            + "order by 2 desc "
            + "limit 10", nativeQuery = true)
    List<Supplier> getTop10SupplierConMasOrdenesDespachadas();

    List<Supplier> findAllByTypeId(long id_tipo);

    @Query(value = "select s.*, CantPorSupplier "
            + "from supplier S inner join  "
            + "(select p.supplier_id ,  COUNT(distinct ppt.id_product_type) as CantPorSupplier "
            + "from product p inner join product_product_type ppt "
            + "on p.id = ppt.id_product "
            + "group by p.supplier_id "
            + "having CantPorSupplier = 4) supplierType  -- (select count(*) from product_type)) supplierType "
            + "on S.id =  supplierType.supplier_id", nativeQuery = true)
    List<Supplier> getProveedoresWithProductosDeTodosLosTipos();
}

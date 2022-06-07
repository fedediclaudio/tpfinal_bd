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
    public List<Supplier> findByNameIgnoreCaseContaining(String supplierName);
   // public Optional<Supplier> findById(long order_id) ;

    @Query(value= "select s.* "
            + "from product P inner join item i on P.id = i.id_product "
            + " inner join orders o on i.id_order = o.id "
            + "inner join order_status os on o.id = os.id_order "
            + "inner join supplier s on p.supplier_id = s.id "
            + "where os.state = 'Delivered' "
            + "group by p.supplier_id "
            + "order by 2 desc "
            + "limit 10", nativeQuery = true)
    public List<Supplier> getTop10SupplierConMasOrdenesDespachadas();

    List<Supplier> findAllByTypeId(long id_tipo);

    @Query(value = "SELECT * from supplier", nativeQuery = true)
    List<Supplier> getProveedoresWithProductosDeTodosLosTipos();
}

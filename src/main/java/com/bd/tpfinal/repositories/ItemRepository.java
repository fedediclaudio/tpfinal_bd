package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>
{
    List<Item> findByOrder(@Param("number") Long number);

    //todos los items de un supplier
    @Query(value = "SELECT item from Item item WHERE item.product.supplier.id = :id_supplier")
    List<Item> findBySupplier(@Param("id_supplier") Long id_supplier);

}

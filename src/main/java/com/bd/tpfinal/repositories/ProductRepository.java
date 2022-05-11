package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    //@Query(value="SELECT producto FROM Product producto WHERE (producto.supplier != null AND producto.id = :id_product)")
    //Optional<Product> findById(@Param("id_product") Long id_product);

    List<Product> findByName(@Param("name") String name);

    @Query(value="SELECT producto FROM Product producto Where producto.type.id = :id_type")
    List<Product> findByTypeId(@Param("id_type")Long id_type );
}

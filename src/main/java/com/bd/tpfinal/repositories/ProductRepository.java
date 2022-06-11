package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductTypeAvgPrice_DTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    @Query(value="SELECT producto FROM Product producto WHERE (producto.id = :id_product AND producto.eliminado != 1)")
    Optional<Product> findById(@Param("id_product") Long id_product);

    List<Product> findByName(@Param("name") String name);

    @Query(value="SELECT producto FROM Product producto WHERE (producto.type.id = :id_type AND producto.eliminado != 1)")
    List<Product> findByTypeId(@Param("id_type") Long id_type );

    @Query(value = "SELECT product FROM Product product WHERE product.supplier.id = :id_supplier")
    List<Product> findBySupplierId(@Param("id_supplier") Long id_supplier);

    //13) Obtener el precio promedio de los productos de cada tipo, para todos los tipos.
    String q13 = "SELECT new com.bd.tpfinal.model.ProductTypeAvgPrice_DTO(p.type.id, AVG(p.price) ) "
            +" FROM Product p "
            +" GROUP BY p.type.id";
    @Query(value=q13)
    List<ProductTypeAvgPrice_DTO> findAllAvgPriceForProductType();

}

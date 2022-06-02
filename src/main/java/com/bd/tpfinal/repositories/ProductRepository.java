package com.bd.tpfinal.repositories;

import com.bd.tpfinal.DTOs.ProductDTO;
import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.bd.tpfinal.model.Product;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQuery;
import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByNameIgnoreCaseContaining(String name);

    List<Product> findBySupplierId(long supplier_id);

    @Query(nativeQuery = true)
    List<ProductoPrecioPromedioDTO> getProductosPrecioPromedioDTO();

    public List<Product> findBySupplierIdAndNameIgnoreCaseContaining(long supplier_id, String name);
}

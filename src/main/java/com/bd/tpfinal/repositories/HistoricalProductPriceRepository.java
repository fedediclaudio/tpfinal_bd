package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.HistoricalProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricalProductPriceRepository extends JpaRepository<HistoricalProductPrice, Long>
{
    /**
     * @param id
     * @return Listado de los HistoricalProductPrice de un Producto. Busca por product.id
     */
    //https://www.baeldung.com/spring-data-jpa-query
    @Query(value = "SELECT h FROM HistoricalProductPrice h WHERE h.product.id = :id")
    List<HistoricalProductPrice> findByProductId(@Param("id") Long id);

}

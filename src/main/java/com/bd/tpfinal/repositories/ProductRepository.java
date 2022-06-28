package com.bd.tpfinal.repositories;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bd.tpfinal.dto.ProductTypePriceDTO;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductPK;

@Repository
public interface ProductRepository extends JpaRepository<Product, ProductPK>{
	
	@Query ("select distinct p from Product p join p.types where p.productPK.idSupplier = ?1 ")
	public Collection<Product> getAllBySupplier(Long s);
	
	@Query (value ="SELECT h.price FROM products p INNER JOIN historical_products_price h"
				+ " ON (h.idsupplier_product = p.id_supplier) AND (h.name_product = p.name)"
		     	+ " WHERE (p.id_supplier = :supplierId) AND (p.name =:nameProducto)"
		     	+ " AND (h.start_date <= :dateTo)"
		     	+ " AND ((h.finish_date >= :dateFrom) OR (h.finish_date is null))", nativeQuery = true)
	public List<Float> getPricesBetweenDates(Long supplierId, String nameProducto, LocalDate dateFrom, LocalDate dateTo);  //for getPricesBetweenDates
	
	@Query ("select new com.bd.tpfinal.dto.ProductTypePriceDTO(t.name, avg(p.price)) from Product p inner join p.types t group by t.name")
	public Collection<ProductTypePriceDTO> getPricesAvgForProducts(); //for getPricesAvgForProducts
	
}
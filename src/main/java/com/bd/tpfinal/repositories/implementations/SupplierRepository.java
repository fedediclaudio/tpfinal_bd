package com.bd.tpfinal.repositories.implementations;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.repositories.interfaces.ISupplierRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long>, ISupplierRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	Supplier getSupplierById(long id);
	
	@Query( value = "SELECT s.* FROM supplier s WHERE s.id_supplier_type = :id", 
			nativeQuery = true)
	List<Supplier> getSupplierListFromType(@Param("id") long id);
	
	@Query( value = "SELECT s.* "
				+ "FROM supplier s, "
				+ "	(SELECT p.id_supplier, SUM(quantity) suma "
				+ "	FROM item i"
				+ "	inner join product p on (i.id_product = p.id_product) "
				+ "	group by p.id_supplier) as suma "
				+ "WHERE s.id_supplier = suma.id_supplier "
				+ "LIMIT 10;", 
				nativeQuery = true)
	List<Supplier> getTopTen();
	
}

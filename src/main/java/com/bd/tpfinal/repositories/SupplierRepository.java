package com.bd.tpfinal.repositories;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bd.tpfinal.dto.SupplierProductsTypesDTO;
import com.bd.tpfinal.dto.SupplierQualificationDTO;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.SupplierType;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>, SupplierRepositoryCustom{
	
	public Collection<Supplier> findByTypesContains(SupplierType st); // for readAllSuppliersForSupplierType
	
	@Query (" select new com.bd.tpfinal.dto.SupplierQualificationDTO (s.id, s.name, s.qualification/s.numberOfQualif, s.numberOfQualif ) from Supplier s where s.numberOfQualif > 0 " )
	public Collection<SupplierQualificationDTO> getQualificationForSuppliers();
	
	@Query (" select new com.bd.tpfinal.dto.SupplierProductsTypesDTO (s.id, s.name, s.cuil, s.address, count(distinct t.name)) from Supplier s inner join s.products p inner join p.types t"
			+ " on  p.productPK.idSupplier = s.id "
			+ " group by s.id"
			+ " having count(distinct t.name) = :cantProductsType")
	public Collection<SupplierProductsTypesDTO> getSuppliersWithProductsAllTypes(Long cantProductsType); // for getSuppliersWithProductsAllTypes
			
}

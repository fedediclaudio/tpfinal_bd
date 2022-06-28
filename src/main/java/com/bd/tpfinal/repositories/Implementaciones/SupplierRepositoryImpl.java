package com.bd.tpfinal.repositories.Implementaciones;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.bd.tpfinal.dto.SupplierOrderDTO;
import com.bd.tpfinal.repositories.SupplierRepositoryCustom;

/**
 * Esta clase contiene la implementación personalizada del repositorio
 * relacionado con las órdenes.
 * 
 **/
public class SupplierRepositoryImpl implements SupplierRepositoryCustom{
	
	/**
	 * Es el administrador de los detalles de la persistencia de JPA.
	 */
	@Autowired
	private EntityManager entityManager;
	
	public Collection<SupplierOrderDTO> getDiezMayorOrderSent(){ //for getDiezMayorOrderSent
		
		EntityManager manager = this.getEntityManager();
		Query query = manager.createQuery("select new com.bd.tpfinal.dto.SupplierOrderDTO(s.id, s.name, s.cuil, s.address, count(distinct o.id)) from Order o"
				+ " inner join o.items i"
				+ " inner join i.product p"
				+ " inner join Supplier s on p.productPK.idSupplier = s.id"
				+ " where o.orderStatus.name = 'Delivered'"
				+ " group by s.id"
				+ " order by count(distinct o.id) desc ");
		Collection<SupplierOrderDTO> result = new ArrayList<SupplierOrderDTO>();
		result.addAll(query.setMaxResults(10).getResultList());
		return result;
	}
	
	/**
	 * Getter.
	 * 
	 * @return el administrador de los detalles de la persistencia de JPA.
	 */
	private EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	
}

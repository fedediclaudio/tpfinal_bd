package com.bd.tpfinal.repositories.Implementaciones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import com.bd.tpfinal.dto.OrderItemsDTO;
import com.bd.tpfinal.repositories.OrderRepositoryCustom;

/**
 * Esta clase contiene la implementación personalizada del repositorio
 * relacionado con las órdenes.
 * 
 **/
public class OrderRepositoryImpl implements OrderRepositoryCustom{
	
	/**
	 * Es el administrador de los detalles de la persistencia de JPA.
	 */
	@Autowired
	private EntityManager entityManager;
	

	public Collection<OrderItemsDTO> getOrdenesForSupplier(Long supplierId){
		
		EntityManager manager = this.getEntityManager();
		Query query = manager.createQuery(" select new com.bd.tpfinal.dto.OrderItemsDTO (o.number, size(o.items)) from Order as o"
				+ "	inner join o.items as i"
				+ "	inner join i.product as p on i.product.productPK = p.productPK"
				+ "	inner join Supplier as s on (s.id = p.productPK.idSupplier) and (s.id = :supplierId)"
				+ "	group by o.number"
				+ "	order by size(o.items) desc ");
		query.setParameter("supplierId", supplierId);
		List<OrderItemsDTO> result = query.getResultList();
		Collection<OrderItemsDTO> resultMaximos = new ArrayList<OrderItemsDTO>();
		int max = result.get(0).getNumberOfItems();
		int i = 0;
		while((i< result.size()) && (result.get(i).getNumberOfItems()==max)) {
				resultMaximos.add(result.get(i));
				i++;
		}
		return resultMaximos;	
	}
	
	/*** Getter.
	 * 
	 * @return el administrador de los detalles de la persistencia de JPA.
	 */
	private EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	
}

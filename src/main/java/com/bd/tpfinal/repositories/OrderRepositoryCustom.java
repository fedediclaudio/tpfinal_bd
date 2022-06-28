package com.bd.tpfinal.repositories;

import java.util.Collection;
import com.bd.tpfinal.dto.OrderItemsDTO;

public interface OrderRepositoryCustom{

	public Collection<OrderItemsDTO> getOrdenesForSupplier(Long supplierId); //for getOrderMasProductsForSupplier	
	
	
	
}

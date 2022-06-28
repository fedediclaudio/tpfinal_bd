package com.bd.tpfinal.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.Supplier;


@Component
public class FactoryDTO {
	
	
	public OrderDTO createOrderDTO(Order anOrder, Collection<ItemDTO> colItemDTO) {
		return new OrderDTO(anOrder.getNumber(), anOrder.getDateOfOrder(), anOrder.getComments(), anOrder.getTotalPrice(), 
				anOrder.getClient().getUsername(), anOrder.getAddress().getAddress(), colItemDTO);
	}
	
	public ItemDTO createItemDTO(Item anItem) {
		return new ItemDTO(anItem.getId(), anItem.getQuantity(), anItem.getProduct().getPrice(), 
				anItem.getProduct().getProductPK().getName(), anItem.getProduct().getProductPK().getIdsupplier());
	}
	
	public SupplierDTO createSupplierDTO (Supplier aSupplier) {
		return new SupplierDTO(aSupplier.getId(), aSupplier.getName(), aSupplier.getCuil(), aSupplier.getAddress(),
				aSupplier.getQualification()/aSupplier.getNumberOfQualif());
	}
	
	public ProductDTO createProductDTO (Product aProduct, List<String> colNamesType) {
		return new ProductDTO(aProduct.getProductPK().getName(), aProduct.getPrice(), aProduct.getWeight(), aProduct.getDescription(), 
				aProduct.getProductPK().getIdsupplier(), colNamesType);
	}
	
}

package com.bd.tpfinal.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.HistoricalProductPrice;
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
		return new ItemDTO(anItem.getQuantity(), anItem.getProduct().getPrice(), 
				anItem.getProduct().getName(), anItem.getProduct().getSupplier().getId());
	}
	
	public SupplierDTO createSupplierDTO (Supplier aSupplier) {
		return new SupplierDTO(aSupplier.getId(), aSupplier.getName(), aSupplier.getCuil(), aSupplier.getAddress(),
				aSupplier.getQualification()/aSupplier.getNumberOfQualif());
	}
	
	
	public ProductDTO createProductDTO (Product aProduct, List<String> colNamesType) {
		return new ProductDTO(aProduct.getName(), aProduct.getPrice(), aProduct.getWeight(), aProduct.getDescription(), 
				aProduct.getSupplier().getId(), colNamesType);
	}
	
	public DeliveryManDTO createDeliveryManDTO (DeliveryMan dm) {
		return new DeliveryManDTO(dm.getId(), dm.getUsername(), dm.getScore());
	}
	
	public SupplierQualificationDTO createSupplierQualificationDTO (Supplier s) {
		return new SupplierQualificationDTO(s.getId(), s.getName(), s.getQualification()/s.getNumberOfQualif(), s.getNumberOfQualif());
	}
	
	public DateDTO createDateDTO(HistoricalProductPrice h) {
		return new DateDTO(h.getPrice(), h.getStartDate(), h.getFinishDate());
	}
}

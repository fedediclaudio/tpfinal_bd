package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
	
	Order createOrder(String idClient) throws Exception;
	Order getOrder(String orderNumber) throws Exception;
	boolean assignAddressToOrder(String orderNumber, String idAddress) throws Exception;
	boolean addProductToOrder(String orderNumber, String idProduct, int quantity, String description) throws Exception;
	boolean cancel(String orderNumber) throws Exception;
	boolean confirmOrder(String orderNumber) throws Exception;
	boolean setQualification(String orderNumber, int score, String comment) throws Exception;
	List<Order> getOrdersFromSupplier(String idSupplier) throws Exception;
	Order getHighestPriceOrderOfDate(LocalDate date) throws Exception;

	List<Order> getOrdersFromSupplierWithMaxProducts(String idSupplier) throws Exception;
}

package com.bd.tpfinal.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.bd.tpfinal.model.Order;

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

	Map<Order, Integer> getOrdersFromSupplierWithMaxProducts(String idSupplier) throws Exception;
}

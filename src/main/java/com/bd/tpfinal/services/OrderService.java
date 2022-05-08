package com.bd.tpfinal.services;

import java.time.LocalDate;
import java.util.List;

import com.bd.tpfinal.model.Order;

public interface OrderService {
	
	Order createOrder(long idClient) throws Exception;
	Order getOrder(int orderNumber) throws Exception;
	boolean assignAddressToOrder(int orderNumber, long idAddress) throws Exception;
	boolean addProductToOrder(int orderNumber, long idProduct, int quantity, String description) throws Exception;
	boolean cancel(int orderNumber) throws Exception;
	boolean confirmOrder(int orderNumber) throws Exception;
	boolean setQualification(int orderNumber, int score, String comment) throws Exception;
	List<Order> getOrdersFromSupplier(long idSupplier) throws Exception;
	Order getHighestPriceOrderOfDate(LocalDate date) throws Exception;
}

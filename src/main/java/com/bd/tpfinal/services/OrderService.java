package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Order;

public interface OrderService {
	
	Order createOrder(long idClient) throws Exception;
	boolean assignAddressToOrder(int orderNumber, long idAddress) throws Exception;
	boolean addProductToOrder(int orderNumber, long idProduct, int quantity, String description) throws Exception;
	boolean cancel(int orderNumber) throws Exception;
	boolean confirmOrder(int orderNumber) throws Exception;
	
}

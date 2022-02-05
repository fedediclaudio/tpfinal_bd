package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Order;

public interface OrderService {
	
	Order createOrder(long idClient) throws Exception;
	boolean assignAddressToOrder(long idOrder, long idAddress) throws Exception;
	boolean addProductToOrder(long idOrder, long idProduct, int quantity, String description) throws Exception;
	boolean cancel(long idOrder) throws Exception;
	boolean confirmOrder(long idOrder) throws Exception;
	
}

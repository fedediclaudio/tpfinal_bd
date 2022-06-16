package com.bd.tpfinal.services;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Order;

@Service
public class OrderServiceImpl implements OrderService {
	
    public Client saveClient(Client client) throws Exception {
    	// Lo hace Pablo
		return null;
    }

    public Order getOrder(String orderNumber) throws Exception {
    	// Lo hace Pablo
		return null;
    }

    public Order createOrder(String idClient) throws Exception {
    	// Lo hace Pablo
		return null;
    }

    public boolean assignAddressToOrder(String orderNumber, String idAddress) throws Exception {
    	// Lo hace Pablo
		return false;
    }

    public boolean addProductToOrder(String orderNumber, String idProduct, int quantity, String description) throws Exception {
    	// Lo hace Pablo
		return false;
    }

    public boolean cancel(String orderNumber) throws Exception {
    	// Lo hace Pablo
		return false;
    }

    public boolean confirmOrder(String orderNumber) throws Exception {
    	// Lo hace Pablo
		return false;
    }
    
    public boolean setQualification(String orderNumber, int score, String comment) throws Exception {
    	// Lo hace Pablo
		return false;
    }

    public List<Order> getOrdersFromSupplier(String idSupplier) throws Exception {
    	// Lo hace Pablo
		return null;
    }

    public Order getHighestPriceOrderOfDate(LocalDate date) throws Exception {
    	// Lo hace Pablo
		return null;
    }

    public Map<Order, Integer> getOrdersFromSupplierWithMaxProducts(String idSupplier) throws Exception {
    	// Lo hace Pablo
		return null;
    }
    
}

package com.bd.tpfinal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;

@Service
public class DeliveryManServiceImpl implements DeliveryManService {

	public DeliveryMan addNewDeliveryMan(DeliveryMan deliveryMan) throws Exception {
		// Lo hace Pablo
		return null;
	}
	
	public long deliveryManCount() throws Exception {
		// Lo hace Pablo
		return 0;
	}
	
	public List<DeliveryMan> getAllDeliveryMan() throws Exception {
		// Lo hace Pablo
		return null;
	}
	
	public List<Order> getAllPendingOrders(String idDeliveryMan) throws Exception {
		// Lo hace Pablo
		return null;
	}
	
	public Order getNextPendingOrder(String idDeliveryMan) throws Exception {
		// Lo hace Pablo
		return null;
	}

	public List<DeliveryMan> getTopTen() throws Exception {
		// Lo hace Pablo
		return null;
	}
	
	public boolean deliverNextPendingOrder(String idDeliveryMan) throws Exception {
		// Lo hace Pablo
		return false;
	}
	
	public boolean refuseNextPendingOrder(String idDeliveryMan) throws Exception {
		// Lo hace Pablo
		return false;
	}
	
	public boolean finishActualOrder(String idDeliveryMan) throws Exception {
		// Lo hace Pablo
		return false;
	}
}

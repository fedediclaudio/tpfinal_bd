package com.bd.tpfinal.model.orderStatusTypes;

import java.time.LocalDate;

import javax.persistence.Entity;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;

@Entity
public class Sent extends OrderStatus {
	
	public Sent() {}
	
	public Sent(Order order) {
		super("Sent", LocalDate.now(), order);
	}
	
	@Override
	public boolean canFinish() {
		return true;
	}

	@Override
	public boolean finish() throws Exception {
		// Se actualizan los puntajes del cliente y del DeliveryMan
		this.getOrder().addClientScore();
		this.getOrder().addDeliveryManScore();
		
		// Configuro al DeliveryMan como libre
		this.getOrder().setDeliveryManBusyTo(false);
		
		return true;
	}
	
}

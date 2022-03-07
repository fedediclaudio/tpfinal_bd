package com.bd.tpfinal.model.orderStatusTypes;

import java.time.LocalDate;

import javax.persistence.Entity;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;

@Entity
public class Assigned extends OrderStatus {
	
	public Assigned() {}
	
	public Assigned(Order order) {
		super("Assigned", LocalDate.now(), order);
	}
	
	@Override
	public boolean canRefuse() {
		return true;
	}

	@Override
	public boolean canDeliver() {
		return true;
	}

	@Override
	public boolean canCancel() {
		return true;
	}

	@Override
	public boolean canChangeAddress() {
		return true;
	}
	
	@Override
	public boolean refuse() throws Exception {
		// Si está asignado y el DeliveryMan rechaza el pedido, entonces resta dos puntos
		this.getOrder().deductDeliveryManScore();
		
		// Cancelo la orden
		this.getOrder().cancelOrder();
		
		// Configuro que no fue cancelada por el usuario
		((Cancel)this.getOrder().getStatus()).setCancelledByClient(false);
		
		return true;
	}

	@Override
	public boolean deliver() throws Exception {
		// Asigno la orden como en Envio
		this.getOrder().setStatus( new Sent(this.getOrder()) );
		
		// Configuro al DeliveryMan como ocupado
		this.getOrder().setDeliveryManBusyTo(true);
		
		return true;
	}

	@Override
	public boolean cancel() throws Exception {
		// Si está asignado y el cliente lo cancela, entonces resta un punto
		this.getOrder().deductClientScore();
		
		// Cancelo la orden
		this.getOrder().cancelOrder();
		// Configuro que fue cancelada por el usuario
		((Cancel)this.getOrder().getStatus()).setCancelledByClient(true);
		
		return true;
	}
	
}

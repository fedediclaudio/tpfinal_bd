package com.bd.tpfinal.model.orderStatusTypes;

import com.bd.tpfinal.model.OrderStatus;

public class Assigned extends OrderStatus {
	
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
		this.getOrder().setStatus( new Sent() );
		
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

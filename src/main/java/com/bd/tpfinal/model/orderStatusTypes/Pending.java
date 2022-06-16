package com.bd.tpfinal.model.orderStatusTypes;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;

@Document(collection = "Pending")
public class Pending extends OrderStatus {
	
	public Pending() {
		this.setName("Pending");
		this.setStartDate( LocalDate.now() );
	}
	
	public Pending(Order order) {
		super("Pending", LocalDate.now(), order);
	}
	
	@Override
	public boolean canAddItem() {
		return true;
	}

	@Override
	public boolean canAssign() {
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
	public boolean assign(DeliveryMan deliveryMan) throws Exception {
		// Se asigna el DeliveryMan a la Orden
		this.getOrder().setDeliveryMan( deliveryMan );
		
		// Se pasa la Orden a estado de Asignado
		this.getOrder().setStatus( new Assigned(this.getOrder()) );
		
		// Remuevo la orden de la lista de pendientes del DeliveryMan
		deliveryMan.removePendingOrder( this.getOrder() );
		
		return true;
	}

	@Override
	public boolean cancel() throws Exception {
		// La orden no esta asignada, asi que no resta puntos a nadie
		this.getOrder().cancelOrder();
		
		// Configuro que fue cancelada por el usuario
		((Cancel)this.getOrder().getStatus()).setCancelledByClient(true);
		
		return true;
	}

}

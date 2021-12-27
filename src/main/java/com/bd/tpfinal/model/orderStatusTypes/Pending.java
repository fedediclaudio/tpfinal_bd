package com.bd.tpfinal.model.orderStatusTypes;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.OrderStatus;

public class Pending extends OrderStatus {
	
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
	public boolean addItem() throws Exception {
		throw new Exception("No se puede realizarse esta accion");
	}

	@Override
	public boolean assign(DeliveryMan deliveryMan) throws Exception {
		throw new Exception("No se puede realizarse esta accion");
	}

	@Override
	public boolean cancel() throws Exception {
		throw new Exception("No se puede realizarse esta accion");
	}

}

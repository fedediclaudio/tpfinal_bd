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
		throw new Exception("No se puede realizarse esta accion");
	}

	@Override
	public boolean deliver() throws Exception {
		throw new Exception("No se puede realizarse esta accion");
	}

	@Override
	public boolean cancel() throws Exception {
		throw new Exception("No se puede realizarse esta accion");
	}
	
}

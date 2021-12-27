package com.bd.tpfinal.model.orderStatusTypes;

import com.bd.tpfinal.model.OrderStatus;

public class Sent extends OrderStatus {
	
	@Override
	public boolean canFinish() {
		return true;
	}

	@Override
	public boolean finish() throws Exception {
		throw new Exception("No se puede realizarse esta accion");
	}
	
}

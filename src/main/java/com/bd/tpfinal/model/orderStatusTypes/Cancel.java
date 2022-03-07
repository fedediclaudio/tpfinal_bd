package com.bd.tpfinal.model.orderStatusTypes;

import java.time.LocalDate;

import javax.persistence.Entity;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;

@Entity
public class Cancel extends OrderStatus {
	private boolean cancelledByClient;

	public Cancel() {}
	
	public Cancel(Order order) {
		super("Cancel", LocalDate.now(), order);
	}
	
	public boolean isCancelledByClient() {
		return cancelledByClient;
	}

	public void setCancelledByClient(boolean cancelledByClient) {
		this.cancelledByClient = cancelledByClient;
	}
}

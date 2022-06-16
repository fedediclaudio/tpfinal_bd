package com.bd.tpfinal.model.orderStatusTypes;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;

@Document(collection = "Cancel")
public class Cancel extends OrderStatus {
	private boolean cancelledByClient;

	public Cancel() {
		this.setName("Cancel");
		this.setStartDate( LocalDate.now() );
	}
	
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

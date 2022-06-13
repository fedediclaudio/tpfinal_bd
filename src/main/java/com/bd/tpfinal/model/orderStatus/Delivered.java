package com.bd.tpfinal.model.orderStatus;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "Delivered")
public class Delivered extends OrderStatus {
	
	public Delivered() {}
	
	public Delivered(Order order) {
		super("Delivered", LocalDate.now(), order);
	}
	
	@Override
	public boolean canRate() {
		return true;
	}
}

package com.bd.tpfinal.model.orderStatusTypes;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;

@Document(collection = "Delivered")
public class Delivered extends OrderStatus {
	
	public Delivered() {
		this.setName("Delivered");
		this.setStartDate( LocalDate.now() );
	}
	
	public Delivered(Order order) {
		super("Delivered", LocalDate.now(), order);
	}
	
	@Override
	public boolean canRate() {
		return true;
	}
}

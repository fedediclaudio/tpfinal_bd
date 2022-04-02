package com.bd.tpfinal.model.orderStatusTypes;

import java.time.LocalDate;

import javax.persistence.Entity;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;

@Entity
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

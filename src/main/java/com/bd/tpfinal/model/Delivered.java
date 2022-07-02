package com.bd.tpfinal.model;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Delivered extends OrderStatus{
	
	public Delivered() { /* empty for framework */ }

	public Delivered(Order order) {
	    super(order, "Delivered");
	}
	    
	public Delivered(Order order, LocalDate startDate) {
	    super(order, "Delivered", startDate);
	}
}

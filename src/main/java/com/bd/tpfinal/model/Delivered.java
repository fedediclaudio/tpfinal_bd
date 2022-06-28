package com.bd.tpfinal.model;

import java.time.LocalDate;

import javax.persistence.*;

@Embeddable
public class Delivered extends OrderStatus{
	
	public Delivered() { /* empty for framework */ }

	public Delivered(Order order) {
	    super(order, "Delivered");
	}
	    
	public Delivered(Order order, LocalDate startDate) {
	    super(order, "Delivered", startDate);
	}
}

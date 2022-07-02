package com.bd.tpfinal.model;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Cancelled extends OrderStatus{
    
    public Cancelled() { /* empty for framework */ }

    public Cancelled(Order order) {
	    super(order, "Cancelled");
	}
	    
	public Cancelled(Order order, LocalDate startDate) {
	    super(order, "Cancelled", startDate);
	}
}

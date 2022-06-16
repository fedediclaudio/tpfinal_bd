package com.bd.tpfinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection = "DeliveryMan")
public class DeliveryMan extends User {
    
	private int numberOfSuccessOrders = 0;

	private boolean free = true;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateOfAdmission;
	
	@DBRef(lazy = true)
    @JsonBackReference(value = "ordersPending")
	private List<Order> ordersPending;

    
	public DeliveryMan() {
		super();
		
		this.numberOfSuccessOrders = 0;
		this.free = true;
		this.dateOfAdmission = LocalDate.now(); 
		this.ordersPending = new ArrayList<Order>();
	}

	public int getNumberOfSuccessOrders() {
		return numberOfSuccessOrders;
	}

	public void setNumberOfSuccessOrders(int numberOfSuccessOrders) {
		this.numberOfSuccessOrders = numberOfSuccessOrders;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public LocalDate getDateOfAdmission() {
		return dateOfAdmission;
	}

	public void setDateOfAdmission(LocalDate dateOfAdmission) {
		this.dateOfAdmission = dateOfAdmission;
	}

	public List<Order> getOrdersPending() {
		return ordersPending;
	}

	public void setOrdersPending(List<Order> ordersPending) {
		this.ordersPending = ordersPending;
	}
	
	public void addPendingOrder(Order order) {
		this.ordersPending.add(order);
	}
	
	public void removePendingOrder(Order order) {
		this.ordersPending.removeIf(o -> o.getNumber() == order.getNumber());
	}
	
	public void deductScore() throws Exception {
		int actualScore = this.getScore();
		this.setScore(actualScore - 2);
	}
	
	public void addScore() throws Exception {
		int actualScore = this.getScore();
		this.setScore(actualScore + 1);
	}
	
	public boolean isValid() {
		if (!super.isValid()) return false;
		
		if (numberOfSuccessOrders < 0) return false;
		if (dateOfAdmission.isAfter( LocalDate.now() )) return false;
		
		return true;
	}
}

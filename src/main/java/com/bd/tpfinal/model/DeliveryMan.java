package com.bd.tpfinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class DeliveryMan extends User {
    
	private int numberOfSuccessOrders;

	private boolean free;

	private LocalDate dateOfAdmission;

	@JsonIgnore
    @OneToMany( mappedBy = "deliveryMan", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY )
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
	
	@Override
	public String toString() {
		return super.toString() + " DeliveryMan [numberOfSuccessOrders=" + numberOfSuccessOrders + ", free=" + free + ", dateOfAdmission="
				+ dateOfAdmission + ", ordersPending=" + ordersPending + "]";
	}
	
	public void deductScore() throws Exception {
		int actualScore = this.getScore();
		this.setScore(actualScore - 2);
	}
	
	public void addScore() throws Exception {
		int actualScore = this.getScore();
		this.setScore(actualScore + 1);
	}
	
}

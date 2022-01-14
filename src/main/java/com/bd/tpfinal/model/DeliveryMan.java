package com.bd.tpfinal.model;

import java.util.Date;
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

	private Date dateOfAdmission;

	@JsonIgnore
    @OneToMany( mappedBy = "deliveryMan", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private List<Order> ordersPending;

    
	public DeliveryMan() {
		super();
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

	public Date getDateOfAdmission() {
		return dateOfAdmission;
	}

	public void setDateOfAdmission(Date dateOfAdmission) {
		this.dateOfAdmission = dateOfAdmission;
	}

	public List<Order> getOrdersPending() {
		return ordersPending;
	}

	public void setOrdersPending(List<Order> ordersPending) {
		this.ordersPending = ordersPending;
	}

	@Override
	public String toString() {
		return "DeliveryMan [numberOfSuccessOrders=" + numberOfSuccessOrders + ", free=" + free + ", dateOfAdmission="
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

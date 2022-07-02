package com.bd.tpfinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document
public class DeliveryMan extends User{
	
	@Field
    private int numberOfSuccessOrders;
	
	@Field
    private boolean free;
	
	@Field
	private LocalDate dateOfAdmission;
	
	@DBRef
	private List<Order> ordersPending = new ArrayList<>();
    
	@DBRef
	private List<Order> ordersDelivered = new ArrayList<>(); //mongo 
	    
    public DeliveryMan() { /*empty for framework */ }
       
    
    public DeliveryMan(String name, String username, String password, String email, LocalDate dateOfBirth) {
		super(name, username, password, email, dateOfBirth);
		this.numberOfSuccessOrders = 0;
        this.free = true;
        this.dateOfAdmission = LocalDate.now(); 
        this.ordersPending = new ArrayList<>();
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

    
	public List<Order> getOrdersDelivered() {
		return ordersDelivered;
	}


	public void setOrdersDelivered(List<Order> ordersDelivered) {
		this.ordersDelivered = ordersDelivered;
	}


	public void addOrder(Order order) {	this.ordersPending.add(order); }

	public void deleteOrder(Order order) {this.ordersPending.remove(order); }
	
	public void addNumberOfSuccessOrders() {
		this.numberOfSuccessOrders++;
		
	}
}

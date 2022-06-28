package com.bd.tpfinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="delivery_men")
public class DeliveryMan extends User{
	
	@Column(nullable = false)
    private int numberOfSuccessOrders;
	
	@Column()
    private boolean free;
	
	@Column(updatable = false, nullable = false)
	private LocalDate dateOfAdmission;
	
	@JsonIgnore
	@OneToMany(mappedBy ="deliveryMan", fetch = FetchType.LAZY)
	private List<Order> ordersPending;
    
	    
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

	public void addOrder(Order order) {	this.ordersPending.add(order); }

	public void deleteOrder(Order order) {this.ordersPending.remove(order); }
	
	public void addNumberOfSuccessOrders() {
		this.numberOfSuccessOrders++;
		
	}
}

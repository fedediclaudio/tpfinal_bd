package com.bd.tpfinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection = "Client")
public class Client extends User {
    
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateOfRegister;
	
	@DBRef(lazy = true)
	@JsonBackReference
	private List<Order> orders;
	
	@DBRef(lazy = true)
	private List<Address> addresses;

	public Client() {
		super();
		this.orders = new ArrayList<Order>();
		this.addresses = new ArrayList<Address>();
	}

	public LocalDate getDateOfRegister() {
		return dateOfRegister;
	}

	public void setDateOfRegister(LocalDate dateOfRegister) {
		this.dateOfRegister = dateOfRegister;
	}

	public Order getOrder(Order order) {
		int idx = this.orders.indexOf(order);
		if (idx == -1) return null;
		return this.orders.get(idx);
	}
	
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void addOrder(Order order) {
		this.orders.add(order);
	}
	
	public void removeOrder(Order order) {
		this.orders.removeIf(o -> o.getNumber() == order.getNumber());
	}
	
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	public void addAddress(Address address) {
		addresses.add(address);
	}
	
	public boolean hasAddress(Address address) {
		return addresses.stream().anyMatch(a -> a.getId().equals(address.getId()));
	}
	
	public void deductScore() throws Exception {
		int actualScore = this.getScore();
		this.setScore(actualScore - 1);
	}
	
	public void addScore() throws Exception {
		int actualScore = this.getScore();
		this.setScore(actualScore + 1);
	}
	
	public boolean isValid() {
		if (!super.isValid()) return false;
		
		if (dateOfRegister.isAfter( LocalDate.now() )) return false;
		
		return true;
	}

}

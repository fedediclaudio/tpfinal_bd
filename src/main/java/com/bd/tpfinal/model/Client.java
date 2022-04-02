package com.bd.tpfinal.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Client extends User {
    
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateOfRegister;
	
	@JsonIgnore
    @OneToMany( mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private List<Order> orders;

    @JsonIgnore
    @OneToMany( mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private List<Address> addresses;

	public Client() {
		super();
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
		return addresses.contains(address);
	}

	@Override
	public String toString() {
		return super.toString() + " Client [dateOfRegister=" + dateOfRegister + ", orders=" + orders + ", addresses=" + addresses + "]";
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

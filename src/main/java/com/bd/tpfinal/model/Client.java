package com.bd.tpfinal.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Client extends User {
    
	private Date dateOfRegister;

    @OneToMany( mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private List<Order> orders;

    @OneToMany( mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private List<Address> addresses;

	public Client() {
		super();
	}

	public Date getDateOfRegister() {
		return dateOfRegister;
	}

	public void setDateOfRegister(Date dateOfRegister) {
		this.dateOfRegister = dateOfRegister;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public String toString() {
		return "Client [dateOfRegister=" + dateOfRegister + ", orders=" + orders + ", addresses=" + addresses + "]";
	}
	
}

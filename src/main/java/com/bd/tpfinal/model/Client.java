package com.bd.tpfinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="clients")
public class Client extends User{

	@Column (updatable = false, nullable = false)
	private LocalDate dateOfRegister;
	
	@JsonIgnore	
	@OneToMany(mappedBy ="client", fetch = FetchType.LAZY)
	private List<Order> orders = new ArrayList<>();
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "client_address_mapped",
	    	   joinColumns = @JoinColumn (name = "id_user"),
	           inverseJoinColumns = @JoinColumn (name = "id_address")) 	
	private Set<Address> addresses = new HashSet<>();

		
	
    public Client() { /* empty for framework */ }
        

	public Client(String name, String username, String password, String email, LocalDate dateOfBirth, Set<Address> addresses){
		super(name, username, password, email, dateOfBirth);
		this.dateOfRegister = LocalDate.now();
        this.orders = new ArrayList<>();
        this.addresses = addresses;
  	}		


    public LocalDate getDateOfRegister() {
		return dateOfRegister;
	}


	public void setDateOfRegister(LocalDate dateOfRegister) {
		this.dateOfRegister = dateOfRegister;
	}


	public List<Order> getOrders() {
        return orders;
    }
	
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public void addOrder(Order order) { this.orders.add(order); }
    
	public void addAddress(Address address) { this.addresses.add(address); }
	
	public void addAddresses(Set<Address> addresses) {this.addresses.addAll(addresses); }
    
}

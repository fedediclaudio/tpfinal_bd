package com.bd.tpfinal.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;


// Documentacion sobre modelado
// https://spring.io/blog/2021/11/29/spring-data-mongodb-relation-modelling

@Document(collection = "Address")
public class Address {
	
	@Id
    private String id;
    
	private String name;

	private String address;

	private String apartment;

	private float[] coords;

	private String description;
	
	@DBRef(lazy = true)
	@JsonBackReference
	private Client client;
	
	@DBRef(lazy = true)
	@JsonBackReference(value = "orders")
	private List<Order> orders;

	
	public Address() {}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public float[] getCoords() {
		return coords;
	}

	public void setCoords(float[] coords) {
		this.coords = coords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public boolean isValid() {
		if (name.isBlank()) return false;
		if (address.isBlank()) return false;
		if ((coords == null) || (coords.length != 2)) return false;
		if (description.isBlank()) return false;
		
		return true;
	}
}

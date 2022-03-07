package com.bd.tpfinal.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Address {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_address")
    private Long id;
    
	private String name;

	private String address;

	private String apartment;

	private float[] coords;

	private String description;

	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn( name="id_client" )
	private Client client;

	@JsonIgnore
	@OneToMany( mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private List<Order> orders;

	
	public Address() {}

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

	@Override
	public String toString() {
		return "Address [id=" + id + ", name=" + name + ", address=" + address + ", apartment=" + apartment
				+ ", coords=" + Arrays.toString(coords) + ", description=" + description + ", orders=" + orders + "]";
	}
	
}

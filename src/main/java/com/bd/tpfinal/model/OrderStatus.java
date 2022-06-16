package com.bd.tpfinal.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonBackReference;

public abstract class OrderStatus {
	
	@Id
    private String id;
    
	private String name;

	private LocalDate startDate;
	
	@DBRef(lazy = true)
	@JsonBackReference
	private Order order;
	
	
 	public OrderStatus() {}
	
	public OrderStatus(String name, LocalDate startDate, Order order) {
		this.name = name;
		this.startDate = startDate;
		this.order = order;
	}
	
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public boolean canAddItem() {
		return false;
	}

	public boolean canAssign() {
		return false;
	}

	public boolean canRefuse() {
		return false;
	}

	public boolean canDeliver() {
		return false;
	}

	public boolean canFinish() {
		return false;
	}

	public boolean canCancel() {
		return false;
	}

	public boolean canChangeAddress() {
		return false;
	}
	
	public boolean canRate() {
		return false;
	}
	
	public boolean assign(DeliveryMan deliveryMan) throws Exception {
		throw new Exception("No se puede realizarse esta accion");
	}

	public boolean refuse() throws Exception {
		throw new Exception("No se puede realizarse esta accion");
	}

	public boolean deliver() throws Exception {
		throw new Exception("No se puede realizarse esta accion");
	}

	public boolean cancel() throws Exception {
		throw new Exception("No se puede realizarse esta accion");
	}

	public boolean finish() throws Exception {
		throw new Exception("No se puede realizarse esta accion");
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}

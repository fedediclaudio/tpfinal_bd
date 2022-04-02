package com.bd.tpfinal.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
public abstract class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_order_status")
	private Long id;
    
	private String name;

	private LocalDate startDate;

	@JsonIgnore
    @OneToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn(name = "order_number", nullable = false)
	private Order order;
	
	
	public OrderStatus() {}
	
	public OrderStatus(String name, LocalDate startDate, Order order) {
		this.name = name;
		this.startDate = startDate;
		this.order = order;
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

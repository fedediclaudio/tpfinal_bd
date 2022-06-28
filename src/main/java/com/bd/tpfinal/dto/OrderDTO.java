package com.bd.tpfinal.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class OrderDTO{
	
    private Long number;
    private LocalDate dateOfOrder;
	private String comments;
    private float totalPrice;
    private String userNameClient;	
	private String address;
	private Collection<ItemDTO> items = new ArrayList<ItemDTO>();
	
	
	public OrderDTO() { /* empty for framework */ } 

	public OrderDTO(Long number, LocalDate dateOfOrder, String comments, float totalPrice, String userNameClient,
			String address, Collection<ItemDTO> items) {
		super();
		this.number = number;
		this.dateOfOrder = dateOfOrder;
		this.comments = comments;
		this.totalPrice = totalPrice;
		this.userNameClient = userNameClient;
		this.address = address;
		this.items = items;
	}


	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public LocalDate getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(LocalDate dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getUserNameClient() {
		return userNameClient;
	}

	public void setUserNameClient(String userNameClient) {
		this.userNameClient = userNameClient;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Collection<ItemDTO> getItems() {
		return items;
	}

	public void setItems(Collection<ItemDTO> items) {
		this.items = items;
	}
	
}

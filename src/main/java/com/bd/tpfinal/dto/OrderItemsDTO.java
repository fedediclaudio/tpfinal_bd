package com.bd.tpfinal.dto;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class OrderItemsDTO{
	
	@JsonSerialize(using= ToStringSerializer.class)
	private ObjectId id;
	private int numberOfItems;
      
    
    public OrderItemsDTO(){ /* empty for framework */ }

	public OrderItemsDTO(ObjectId id, int numberOfItems) {
		this.id = id;
		this.numberOfItems = numberOfItems;
	}

	public ObjectId getId() {
		return id;
	}


	public void setId(ObjectId id) {
		this.id = id;
	}


	public int getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	@Override
	public String toString() {
		return "OrderItemsDTO [id=" + id + ", numberOfItems=" + numberOfItems + "]";
	}

	
}

package com.bd.tpfinal.dto;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class ItemDTO {
	
	private int quantity;
    private float price;
    private String name;
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId idSupplier;
    
    public ItemDTO() { /* empty for framework */ }  
    
	public ItemDTO(int quantity, float price, String name, ObjectId idSupplier) {
		this.quantity = quantity;
		this.price = price;
		this.name = name;
		this.idSupplier = idSupplier;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ObjectId getIdSupplier() {
		return idSupplier;
	}

	public void setIdSupplier(ObjectId idSupplier) {
		this.idSupplier = idSupplier;
	}

    
}
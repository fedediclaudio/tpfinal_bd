package com.bd.tpfinal.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

public class Item {
	
	@Field
	private int quantity;
	
	@Field
    private String description;

	@NotNull(message ="product is required")
	@DBRef
    private Product product;
    
	@Version
	private int version;
    
    public Item() {	/* empty for framework */ }    
    
    public Item(Product product, int quantity, String description) {
		this.quantity = quantity;
		this.description = description;
		this.product = product;
	}    


	public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity; 
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
   
}

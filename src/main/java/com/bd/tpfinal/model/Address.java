package com.bd.tpfinal.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document
public class Address {
	
	@MongoId
	@JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;
	
	@Field
	@NotNull(message = "address is required")
   	@NotBlank(message ="address can't be blank")
    private String address;
	
	@Field
    private String apartment;
	
	@Field
    private float[] coords;
	
	@Field
    private String description;
	
	   
    
    public Address () { /* empty for framework */ }
       
    public Address(String address, String apartment, float[] coords, String description) {
		this.address = address;
		this.apartment = apartment;
		this.coords = coords;
		this.description = description;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getId() {
        return id;
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
    
}

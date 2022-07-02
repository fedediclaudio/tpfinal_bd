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
public class ProductType {
	
	@MongoId
	@JsonSerialize(using= ToStringSerializer.class)
	private ObjectId id;
	
	@NotNull(message = "name is required")
   	@NotBlank(message ="name can't be blank")
	@Field 
    private String name;
	
	@Field
    private String description;
	
    public ProductType() { /* empty for framework */ }
    	
    public ProductType(String name, String description) {
    	
    	this.name = name;
    	this.description = description;
    }
    
    public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}

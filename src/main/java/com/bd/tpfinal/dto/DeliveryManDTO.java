package com.bd.tpfinal.dto;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class DeliveryManDTO {
	
	@JsonSerialize(using= ToStringSerializer.class)
	private ObjectId id;
	private String username;
	private Integer score;
    
    public DeliveryManDTO(){ /* empty for framework */ }
    
    

	public DeliveryManDTO(ObjectId id, String username, Integer score) {
		super();
		this.id = id;
		this.username = username;
		this.score = score;
	}



	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	

	
}

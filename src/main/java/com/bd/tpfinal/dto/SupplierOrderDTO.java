package com.bd.tpfinal.dto;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class SupplierOrderDTO {
	
	@JsonSerialize(using= ToStringSerializer.class)
	private ObjectId id;
	private int numberOfOrdersDelivered;
    
    
    public SupplierOrderDTO(){ /* empty for framework */ }
	
	public SupplierOrderDTO(ObjectId id, int numberOfOrdersDelivered) {
		this.id = id;
		this.numberOfOrdersDelivered = numberOfOrdersDelivered;
	}


	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public int getNumberOfOrdersDelivered() {
		return numberOfOrdersDelivered;
	}

	public void setNumberOfOrdersDelivered(int numberOfOrdersDelivered) {
		this.numberOfOrdersDelivered = numberOfOrdersDelivered;
	}
	
}

package com.bd.tpfinal.dto;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class SupplierDTO {
	
	@JsonSerialize(using= ToStringSerializer.class)
	private ObjectId id;
	private String name;
    private String cuil;
    private String address;
    private float  qualificationAvg;
    
    public SupplierDTO(){ /* empty for framework */ }
	

	public SupplierDTO(ObjectId id, String name, String cuil, String address, float qualificationAvg){
		super();
		this.id = id;
		this.name = name;
		this.cuil = cuil;
		this.address = address;
		this.qualificationAvg = qualificationAvg;
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

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getQualificationAvg() {
		return qualificationAvg;
	}

	public void setQualificationAvg(float qualificationAvg) {
		this.qualificationAvg = qualificationAvg;
	}

}

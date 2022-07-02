package com.bd.tpfinal.dto;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class SupplierQualificationDTO {

	@JsonSerialize(using= ToStringSerializer.class)
	private ObjectId id;
	private String name;
	private float qualificationPromedioOfUser;
	private int numberOfQualif;
	
	
	public SupplierQualificationDTO(){ /* empty for framework */ }
	
	
	public SupplierQualificationDTO(ObjectId id, String name, float qualificationPromedioOfUser, int numberOfQualif) {
		super();
		this.id = id;
		this.name = name;
		this.qualificationPromedioOfUser = qualificationPromedioOfUser;
		this.numberOfQualif = numberOfQualif;
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
	public float getQualificationPromedioOfUser() {
		return qualificationPromedioOfUser;
	}
	public void setQualificationPromedioOfUser(float qualificationPromedioOfUser) {
		this.qualificationPromedioOfUser = qualificationPromedioOfUser;
	}
	public int getNumberOfQualif() {
		return numberOfQualif;
	}
	public void setNumberOfQualif(int numberOfQualif) {
		this.numberOfQualif = numberOfQualif;
	}

	@Override
	public String toString() {
		return "SupplierQualificationDTO1 [id=" + id + ", name=" + name + ", qualificationPromedioOfUser="
				+ qualificationPromedioOfUser + ", numberOfQualif=" + numberOfQualif + "]";
	}
	
	
}




package com.bd.tpfinal.dto;

public class SupplierQualificationDTO {

	
	private Long id;
	private String name;
	private float qualificationPromedioOfUser;
	private int numberOfQualif;
	
	
	public SupplierQualificationDTO(){ /* empty for framework */ }
	
	
	public SupplierQualificationDTO(Long id, String name, float qualificationPromedioOfUser, int numberOfQualif) {
		super();
		this.id = id;
		this.name = name;
		this.qualificationPromedioOfUser = qualificationPromedioOfUser;
		this.numberOfQualif = numberOfQualif;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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




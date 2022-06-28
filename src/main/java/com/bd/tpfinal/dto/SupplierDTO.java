package com.bd.tpfinal.dto;

public class SupplierDTO {
	
	private Long id;
	private String name;
    private String cuil;
    private String address;
    private float  qualificationAvg;
    
    public SupplierDTO(){ /* empty for framework */ }
	

	public SupplierDTO(Long id, String name, String cuil, String address, float qualificationAvg){
		super();
		this.id = id;
		this.name = name;
		this.cuil = cuil;
		this.address = address;
		this.qualificationAvg = qualificationAvg;
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

package com.bd.tpfinal.dto;


public class SupplierOrderDTO {
	
	private Long id;
	private String name;
    private String cuil;
    private String address;
    private Long numberOfOrdersDelivered;
    
    
    public SupplierOrderDTO(){ /* empty for framework */ }
	
	public SupplierOrderDTO(Long id, String name, String cuil, String address, Long numberOfOrdersDelivered) {
		this.id = id;
		this.name = name;
		this.cuil = cuil;
		this.address = address;
		this.numberOfOrdersDelivered = numberOfOrdersDelivered;
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

	public Long getNumberOfOrdersDelivered() {
		return numberOfOrdersDelivered;
	}

	public void setNumberOfOrdersDelivered(Long numberOfOrdersDelivered) {
		this.numberOfOrdersDelivered = numberOfOrdersDelivered;
	}

	
}

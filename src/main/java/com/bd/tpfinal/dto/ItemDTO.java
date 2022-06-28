package com.bd.tpfinal.dto;

public class ItemDTO {
	
	private Long id;
	private int quantity;
    private float price;
    private String name;
    private Long idSupplier;
    
    public ItemDTO() { /* empty for framework */ }  
    
	public ItemDTO(Long id, int quantity, float price, String name, Long idSupplier) {
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.name = name;
		this.idSupplier = idSupplier;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIdSupplier() {
		return idSupplier;
	}

	public void setIdSupplier(Long idSupplier) {
		this.idSupplier = idSupplier;
	}

    
}
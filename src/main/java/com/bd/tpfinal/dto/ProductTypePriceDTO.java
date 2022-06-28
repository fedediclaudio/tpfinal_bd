package com.bd.tpfinal.dto;


public class ProductTypePriceDTO {
			
    private String name;
    private double price;
    
    public ProductTypePriceDTO () { /* empty for framework */ }
    
	public ProductTypePriceDTO(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductTypePriceDTO [name=" + name + ", price=" + price + "]";
	}
	
	
	
}

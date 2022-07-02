package com.bd.tpfinal.dto;


public class ProductTypePriceDTO {
			
    private String name;
    private double priceProm;
    
    public ProductTypePriceDTO () { /* empty for framework */ }
    
	public ProductTypePriceDTO(String name, double priceProm) {
		this.name = name;
		this.priceProm = priceProm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPriceProm() {
		return priceProm;
	}

	public void setPrice(double priceProm) {
		this.priceProm = priceProm;
	}

	@Override
	public String toString() {
		return "ProductTypePriceDTO [name=" + name + ", priceProm=" + priceProm + "]";
	}
	
	
	
}

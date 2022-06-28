package com.bd.tpfinal.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {	
	
	private String name;
	private float price;
    private float weight;
    private String description;
    private Long idSupplier;
    private List<String> namesType = new ArrayList<String>(); ;
    
	public ProductDTO() {}

	public ProductDTO(String name, float price, float weight, String description, Long idSupplier, List<String> namesType ) {
		this.name = name;
		this.price = price;
		this.weight = weight;
		this.description = description;
		this.idSupplier = idSupplier;
		this.namesType = namesType;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getIdSupplier() {
		return idSupplier;
	}

	public void setIdSupplier(Long idSupplier) {
		this.idSupplier = idSupplier;
	}

	public List<String> getNamesType() {
		return namesType;
	}

	public void setNameType(List<String> namesType) {
		this.namesType = namesType;
	}

	@Override
	public String toString() {
		return "ProductDTO [name=" + name + ", price=" + price + ", weight=" + weight + ", description=" + description
				+ ", idSupplier=" + idSupplier + ", namesType=" + namesType + "]";
	}

}

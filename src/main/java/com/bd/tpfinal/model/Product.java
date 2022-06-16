package com.bd.tpfinal.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Product")
public class Product {

	@Id
    private String id;
	
	private String name;

	private float price;

	private float weight;

	private String description;

	private Supplier supplier;

	private ProductType type;

	@DBRef(lazy = true)
	private List<HistoricalProductPrice> prices;

	private boolean productDeleted; 
	
	
	public Product() {
		this.prices = new ArrayList<HistoricalProductPrice>();
		this.productDeleted = false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public List<HistoricalProductPrice> getPrices() {
		return prices;
	}

	public void setPrices(List<HistoricalProductPrice> prices) {
		this.prices = prices;
	}
	
	public void addHistoricalPrice(HistoricalProductPrice historical) {
		this.prices.add(historical);
	}

	public boolean isProductDeleted() {
		return productDeleted;
	}

	public void setProductDeleted(boolean productDeleted) {
		this.productDeleted = productDeleted;
	}	
}

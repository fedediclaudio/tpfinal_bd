package com.bd.tpfinal.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Document(collection = "ProductType")
public class ProductType {

	@Id
    private String id;
	private String name;
	private String description;

	@DBRef(lazy = true)
	@DocumentReference
	@JsonBackReference
	private List<Product> products;

	private Double avgProductsPrice;
	
	public ProductType() {
		this.products = new ArrayList<Product>();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product) {
		this.products.add(product);
	}
	
	public Double getAvgProductsPrice() {
		return avgProductsPrice;
	}

	public void setAvgProductsPrice(Double avgProductsPrice) {
		this.avgProductsPrice = avgProductsPrice;
	}

}

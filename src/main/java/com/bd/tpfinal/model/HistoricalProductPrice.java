package com.bd.tpfinal.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class HistoricalProductPrice {

	@Id
    private String id;
	
	private float price;

	private LocalDate startDate;

	private LocalDate finishDate;

	@DBRef(lazy = true)
	@JsonBackReference(value = "product")
	private Product product;
	
	
	public HistoricalProductPrice() {}

	public HistoricalProductPrice(Product product) {
		this.price = product.getPrice();
		this.startDate = LocalDate.now();
		this.product = product;
		this.finishDate = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(LocalDate finishDate) {
		this.finishDate = finishDate;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}

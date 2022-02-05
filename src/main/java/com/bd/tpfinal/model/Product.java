package com.bd.tpfinal.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product")
    private Long id;
	
	private String name;

	private float price;

	private float weight;

	private String description;

	@ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn( name = "id_supplier" )
	private Supplier supplier;

	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn( name="id_type" )
	private ProductType type;

	@JsonIgnore
	@OneToMany( mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private List<HistoricalProductPrice> prices;

	
	public Product() {
		this.prices = new ArrayList<>();
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
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", weight=" + weight + ", description="
				+ description + ", supplier=" + supplier + ", type=" + type + ", prices=" + prices + "]";
	}
	
}

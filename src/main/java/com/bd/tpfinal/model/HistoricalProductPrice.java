package com.bd.tpfinal.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class HistoricalProductPrice {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_historical_product_price")
    private Long id;
	
	private float price;

	private LocalDate startDate;

	private LocalDate finishDate;

	@ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinColumn( name = "id_product" )
	private Product product;

	
	public HistoricalProductPrice() {}
	
	public HistoricalProductPrice(Product product) {
		this.product = product;
		this.price = product.getPrice();
		this.startDate = LocalDate.now();
		this.finishDate = null;
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

	@Override
	public String toString() {
		return "HistoricalProductPrice [id=" + id + ", price=" + price + ", startDate=" + startDate + ", finishDate="
				+ finishDate + ", product=" + product + "]";
	}
	
}

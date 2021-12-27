package com.bd.tpfinal.model;

import java.util.Date;

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

	private Date startDate;

	private Date finishDate;

	@ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn( name = "id_product" )
	private Product product;

	
	public HistoricalProductPrice() {}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
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

package com.bd.tpfinal.model;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name="historical_products_price")
public class HistoricalProductPrice {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column (nullable = false)
    private float price;
	
	@Column (nullable = false)
	private LocalDate startDate;
	
	@Column ()
	private LocalDate finishDate;
    
    @Version
	private int version;
    
    public HistoricalProductPrice() { /* empty for framework */ } 
    
    public HistoricalProductPrice(float price, LocalDate startDate) {
		this.price = price;
		this.startDate = startDate;
		this.finishDate = null;
	}
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
    
    public int getVersion() {
    	return version;
    }

    public void setVersion(int version) {
    	this.version = version;
    }
}

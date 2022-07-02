package com.bd.tpfinal.model;

import java.time.LocalDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Field;

public class HistoricalProductPrice {
	
	@Field
    private float price;
	
	@Field
	private LocalDate startDate;
	
	@Field
	private LocalDate finishDate;
    
    @Version
	private int version;
    
    public HistoricalProductPrice() { /* empty for framework */ } 
    
    public HistoricalProductPrice(float price, LocalDate startDate) {
		this.price = price;
		this.startDate = startDate;
		this.finishDate = null;
	}

    public HistoricalProductPrice(float price, LocalDate startDate, LocalDate finishDate) {
		this.price = price;
		this.startDate = startDate;
		this.finishDate = finishDate;
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

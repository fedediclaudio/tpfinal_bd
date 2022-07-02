package com.bd.tpfinal.dto;

import java.time.LocalDate;


public class DateDTO {
	
    private float price;
	private LocalDate startDate;
	private LocalDate finishDate;
   
    public DateDTO() { /* empty for framework */ }

	public DateDTO(float price, LocalDate startDate, LocalDate finishDate) {
		super();
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
    
	
}
package com.bd.tpfinal.dto;


public class OrderItemsDTO{
	
	private long number;
	private int numberOfItems;
      
    
    public OrderItemsDTO(){ /* empty for framework */ }

	public OrderItemsDTO(long number, int numberOfItems) {
		this.number = number;
		this.numberOfItems = numberOfItems;
	}

	public long getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public int getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	@Override
	public String toString() {
		return "OrderItemsDTO [number=" + number + ", numberOfItems=" + numberOfItems + "]";
	}

	
}

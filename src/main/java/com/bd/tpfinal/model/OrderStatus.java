package com.bd.tpfinal.model;

import java.time.LocalDate;
import javax.persistence.*;

import com.bd.tpfinal.utils.DeliveryException;

@Embeddable
public class OrderStatus {
	
	@Column(name= "state")
	private String name;
	
	@Column(name= "start_date_state")
	protected LocalDate startDate;
	
	@Transient
	protected Order order;    
	
    
	public OrderStatus(){ /* empty for framework */ }

    public OrderStatus(Order order, String name) {
        this.order = order;
        this.name = name;
        this.startDate = LocalDate.now();
    }        
	public OrderStatus(Order order, String name, LocalDate startDate) {
		this.name = name;
		this.startDate = startDate;
		this.order = order;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean canAddItem() { return false; }

    public boolean canAssigned() { return false; }

    public boolean canRefused() { return false; }

    public boolean canDelivered() { return false; }

    public boolean canFinished() { return false; }

    public boolean canCancelled() { return false; }
    
    public boolean isSent() { return (this.name == "Sent");}    
    
    public boolean addItem(Item item){
        throw new DeliveryException("This action can't be performed");
    }

    public boolean assign(DeliveryMan deliveryMan){
        throw new DeliveryException("The order can't be assigned");
    }

    public boolean refuse(){
        throw new DeliveryException("The order can't be refused");
    }

    public boolean deliver(){
        throw new DeliveryException("The order can't be delivered");
    }

    public boolean cancel(){
        throw new DeliveryException("The order can't be cancelled");
    }

    public boolean finish(){
        throw new DeliveryException("The order can't be finish");
    }

    
}

package com.bd.tpfinal.model;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import com.bd.tpfinal.utils.DeliveryException;

@Document
public class Assigned extends OrderStatus{
	
	public Assigned() { /* empty for framework */ }
	
    public Assigned(Order order) {
	    super(order, "Assigned");
	}
	    
    public Assigned(Order order, LocalDate startDate){
        super(order, "Assigned", startDate);
    }
    
    @Override
	public boolean canCancelled() {
		return true;
	}
    
	@Override
	public boolean canRefused() {
		return true;
	}
	
	@Override
	public boolean canDelivered() {
		return true;
	}
	
	
	@Override
	public boolean cancel(){
		if(this.canCancelled()){
            this.order.setOrderStatus(new Cancelled(this.order));
            this.order.getDeliveryMan().deleteOrder(order);
            this.order.getDeliveryMan().setFree(true);
            this.order.getClient().addScore(-2);
            return true;
        } else {
            throw new DeliveryException("The order can't be cancelled");
        }
    }
	
	@Override
	public boolean refuse(){
		if(this.canRefused()) {
            this.order.setOrderStatus(new Cancelled(this.order));
           
            this.order.getDeliveryMan().addScore(-2);
            this.order.getDeliveryMan().deleteOrder(order);
            this.order.getDeliveryMan().setFree(true);
            return true;
        } else {
            throw new DeliveryException("The order can't be refused");
        }
	}
	@Override
	public boolean deliver(){
		if(this.canDelivered()) {
            this.order.setOrderStatus(new Sent(this.order));
            return true;
        } else {
            throw new DeliveryException("The order can't be delivered");
        }
	}
    
}

package com.bd.tpfinal.model;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import com.bd.tpfinal.utils.DeliveryException;

@Document
public class Sent extends OrderStatus{
	
	public Sent() {	/*empty for framework */ }

    public Sent(Order order) {
        super(order, "Sent");
    }
    
    public Sent(Order order, LocalDate startDate) {
        super(order, "Sent", startDate);
    }

	@Override
	public boolean canFinished() {
		return true;
	}

	@Override
	public boolean finish(){
		try {
		this.order.setOrderStatus(new Delivered(this.order));
        this.order.getDeliveryMan().addScore(1);
        this.order.getDeliveryMan().addNumberOfSuccessOrders();      
        this.order.getDeliveryMan().setFree(true);
        this.order.getDeliveryMan().getOrdersPending().remove(order);
        this.order.getDeliveryMan().getOrdersDelivered().add(order);
        
        
        this.order.getClient().addScore(1);
        return true;
		}catch (Exception e) { 
             new DeliveryException("The order can't be delivered");}
		return false;
	}
    
	
}

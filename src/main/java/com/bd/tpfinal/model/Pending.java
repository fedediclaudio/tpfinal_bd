package com.bd.tpfinal.model;

import java.time.LocalDate;

import javax.persistence.*;

import com.bd.tpfinal.utils.DeliveryException;


@Embeddable
public class Pending extends OrderStatus{

	public Pending() {/* empty for framework */ }

	public Pending(Order order) {
	    super(order, "Pending");
	}
	    
	public Pending (Order order, LocalDate date) {
	    super(order, "Pending", date);
	}
	
	@Override
	public boolean canAssigned() {
		  return true;
	}
	
	@Override
	public boolean canCancelled() {
		return true;
	}

	@Override
	public boolean canAddItem() {
		return true;
	}
	
	@Override
	public boolean addItem(Item item){
			if (this.canAddItem()) {
            this.order.addItem(item);
            return true;
        } else {
            throw new DeliveryException("The order cant'be add a new item");
        }
	}
	
	@Override
	public boolean assign(DeliveryMan deliveryMan) {
		if (this.canAssigned()) {
            deliveryMan.addOrder(this.order);
            deliveryMan.setFree(false);
            this.order.setDeliveryMan(deliveryMan);
            this.order.setOrderStatus(new Assigned(this.order));
            return true;
        } else {
            throw new DeliveryException("The order can't be assigned");
        }
	}

	@Override
	public boolean cancel(){
		if (this.canCancelled()) {
            this.order.setOrderStatus(new Cancelled(this.order));
            this.order.getClient().addScore(-1);
            return true;
        } else {
            throw new DeliveryException("The order can't be cancelled");
        }
	}
	
}

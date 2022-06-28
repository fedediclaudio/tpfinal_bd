package com.bd.tpfinal.model;

import java.time.LocalDate;
import javax.persistence.Embeddable;
import com.bd.tpfinal.utils.DeliveryException;

@Embeddable
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
        this.order.getDeliveryMan().deleteOrder(order);
        this.order.setDeliveryMan(null);// Rompo la relacion bidireccional(no hay otra forma en el esquema de db actual)
        this.order.getClient().addScore(1);
        return true;
		}catch (Exception e) { 
             new DeliveryException("The order can't be delivered");}
		return false;
	}
    
	
}

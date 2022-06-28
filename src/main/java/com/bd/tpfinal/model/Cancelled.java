package com.bd.tpfinal.model;


import java.time.LocalDate;

import javax.persistence.Embeddable;

@Embeddable
public class Cancelled extends OrderStatus{
	
   
    private boolean cancelledByClient;
    
    
    public Cancelled() { /* empty for framework */ }

    public Cancelled(Order order) {
	    super(order, "Cancelled");
	}
	    
	public Cancelled(Order order, LocalDate startDate) {
	    super(order, "Cancelled", startDate);
	}

    public boolean isCancelledByClient() {
        return cancelledByClient;
    }

    public void setCancelledByClient(boolean cancelledByClient) {
        this.cancelledByClient = cancelledByClient;
    }
}

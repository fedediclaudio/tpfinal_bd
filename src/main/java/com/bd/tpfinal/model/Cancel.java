package com.bd.tpfinal.model;

import java.util.Date;

public class Cancel extends OrderStatus{

    private boolean cancelledByClient;

    public boolean isCancelledByClient() {
        return cancelledByClient;
    }

    public void setCancelledByClient(boolean cancelledByClient) {
        this.cancelledByClient = cancelledByClient;
    }


    public Cancel(){}

    public Cancel(Order order ){ super (order,  "Cancel");}
    public Cancel(Order order, Date startDate ){ super (order, "Cancel", startDate);}
}

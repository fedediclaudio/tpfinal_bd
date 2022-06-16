package com.bd.tpfinal.model;

import java.util.Date;

public class Pending extends OrderStatus{
    public Pending(){}
    public Pending(Order order ){ super (order,  "Pending");}
    public Pending(Order order, Date startDate ){ super (order, "Pending", startDate);}

    @Override
    public boolean canAssign(){ return true;}

    @Override
    public boolean canCancel (){return true;}

}

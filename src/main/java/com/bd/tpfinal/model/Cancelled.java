package com.bd.tpfinal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.util.Date;
//@Entity
//@DiscriminatorValue("Cancelled")
@Embeddable
public class Cancelled extends OrderStatus
{
    //private Order order;

    private boolean cancelledByClient;

    public Cancelled(Order order, Date start_date)
    {
        super(order, "Cancelled", start_date);
        //setOrder_status_enum(Order_Status_Enum.CANCELLED);
    }

    @Override
    public boolean canAssign()
    {
        return false;
    }

    @Override
    public boolean canRefuse()
    {
        return false;
    }

    @Override
    public boolean canDeliver()
    {
        return false;
    }

    @Override
    public boolean canFinish()
    {
        return false;
    }

    public boolean isCancelledByClient()
    {
        return cancelledByClient;
    }

    public void setCancelledByClient(boolean cancelledByClient)
    {
        this.cancelledByClient = cancelledByClient;
    }


}

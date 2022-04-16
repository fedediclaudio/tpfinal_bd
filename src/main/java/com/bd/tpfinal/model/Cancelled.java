package com.bd.tpfinal.model;

import java.util.Date;

public class Cancelled extends OrderStatus
{
    //private Order order;

    private boolean cancelledByClient;

    public Cancelled(Order order, String name, Date start_date)
    {
        super.setOrder(order);
        super.setName(name);
        super.setStartDate(start_date);
        setOrder_status_enum(Order_Status_Enum.CANCELLED);
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

package com.bd.tpfinal.model;

public class Cancel extends OrderStatus
{
    private Order order;

    private boolean cancelledByClient;

    public Cancel(Order order)
    {
        this.order = order;
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

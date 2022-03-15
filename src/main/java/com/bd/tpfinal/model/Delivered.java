package com.bd.tpfinal.model;

public class Delivered extends OrderStatus
{
    private Order order;

    public Delivered(Order order)
    {
        this.order = order;
    }

}

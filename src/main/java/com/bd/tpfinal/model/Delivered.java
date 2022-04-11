package com.bd.tpfinal.model;

public class Delivered extends OrderStatus
{
    //private Order order;

    public Delivered(Order order)
    {
        super.setOrder(order);
        super.setOrder_status_enum(Order_Status_Enum.DELIVERED);
    }

}

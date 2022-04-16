package com.bd.tpfinal.model;

import java.util.Date;

public class Delivered extends OrderStatus
{
    //private Order order;

    public Delivered(Order order, String name, Date start_date)
    {
        super.setOrder(order);
        super.setName(name);
        super.setStartDate(start_date);
        setOrder_status_enum(Order_Status_Enum.DELIVERED);
    }
}

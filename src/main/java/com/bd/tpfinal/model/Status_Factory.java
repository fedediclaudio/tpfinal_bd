package com.bd.tpfinal.model;

import java.util.Date;

public class Status_Factory
{
    private Status_Factory()
    {

    }
    public static OrderStatus getInstance(Order_Status_Enum order_status_enum, Order order, String name, Date start_date)
    {
        OrderStatus orderStatus = null;
        if(order_status_enum.equals(Order_Status_Enum.PENDING))
        {
            orderStatus = new Pending(order, name, start_date);
        }
        else if(order_status_enum.equals(Order_Status_Enum.ASSIGNED))
        {
            orderStatus = new Assigned(order);
        }
        else if(order_status_enum.equals(Order_Status_Enum.DELIVERED))
        {
            orderStatus = new Delivered(order);
        }
        else if(order_status_enum.equals(Order_Status_Enum.SENT))
        {
            orderStatus = new Sent(order);
        }
        else
        {
            orderStatus = new Cancel(order);
        }
        orderStatus.setOrder_status_enum(order_status_enum);
        return orderStatus;
    }
}

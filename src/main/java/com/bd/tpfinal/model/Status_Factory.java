package com.bd.tpfinal.model;

public class Status_Factory
{
    private Status_Factory()
    {

    }
    public static OrderStatus getInstance(Order_Status_Enum order_status_enum, Order order)
    {
        OrderStatus orderStatus = null;
        if(order_status_enum.equals(Order_Status_Enum.PENDING))
        {
            orderStatus = new Pending(order);
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
        return orderStatus;
    }
}

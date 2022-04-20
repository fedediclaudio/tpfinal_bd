package com.bd.tpfinal.model;

import com.bd.tpfinal.model.Exceptions.ErrorStatusException;

import java.util.Date;

public class Status_Factory
{
    private Status_Factory()
    {

    }

    //public static OrderStatus getInstance(Order_Status_Enum order_status_enum, Order order, String name, Date start_date)  throws ErrorStatusException
    public static OrderStatus getInstance(Order_Status_Enum order_status_enum, Order order, String name, Date start_date)
    {
        OrderStatus orderStatus = null;
        if(name.equals("Pending"))
        {
            orderStatus = new Pending(order, start_date);
        }
        else if(name.equals("Assigned"))
        {
            orderStatus = new Assigned(order, start_date);
        }
        else if(name.equals("Delivered"))
        {
            orderStatus = new Delivered(order, start_date);
        }
        else if(name.equals("Sent"))
        {
            orderStatus = new Sent(order, start_date);
        }
        else if(name.equals("Cancelled"))
        {
            orderStatus = new Cancelled(order, start_date);
        }
        //orderStatus.setOrder_status_enum(order_status_enum);
        //else
            //throw new ErrorStatusException(name);
        return orderStatus;
    }
}

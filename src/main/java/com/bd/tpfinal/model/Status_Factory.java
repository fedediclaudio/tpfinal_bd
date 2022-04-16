package com.bd.tpfinal.model;

import java.util.Date;

public class Status_Factory
{
    private Status_Factory()
    {

    }

    /**
     * esto esta hecho con la intención de recuperar
     * el tipo de objeto "Hijo de OrderStatus"
     * luego de persisir (al despersistir)
     * @param status
     * @return
     */
    public static OrderStatus getInstanceByEnum(OrderStatus status)
    {
        OrderStatus orderStatus;
        if(status.getOrder_status_enum().equals(Order_Status_Enum.PENDING))
        {
            orderStatus = (Pending)status;
        }
        else if(status.getOrder_status_enum().equals(Order_Status_Enum.ASSIGNED))
        {
            orderStatus = (Assigned)status;
        }
        else if(status.getOrder_status_enum().equals(Order_Status_Enum.DELIVERED))
        {
            orderStatus = (Delivered)status;
        }
        else if(status.getOrder_status_enum().equals(Order_Status_Enum.SENT))
        {
            orderStatus = (Sent)status;
        }
        else
        {
            orderStatus = (Cancelled)status;
        }
        return orderStatus;
    }

    public static OrderStatus getInstanceByOrderStatus(OrderStatus orderStatus)
    {
        return getInstance(orderStatus.getOrder_status_enum(), orderStatus.getOrder(), orderStatus.getName(),orderStatus.getStartDate());
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
            orderStatus = new Assigned(order, name, start_date);
        }
        else if(order_status_enum.equals(Order_Status_Enum.DELIVERED))
        {
            orderStatus = new Delivered(order, name, start_date);
        }
        else if(order_status_enum.equals(Order_Status_Enum.SENT))
        {
            orderStatus = new Sent(order, name, start_date);
        }
        else
        {
            orderStatus = new Cancelled(order,name, start_date);
        }
        orderStatus.setOrder_status_enum(order_status_enum);
        return orderStatus;
    }
}

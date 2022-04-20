package com.bd.tpfinal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.util.Date;
//@Entity
//@DiscriminatorValue("Delivered")
@Embeddable
public class Delivered extends OrderStatus
{
    //private Order order;

    public Delivered(Order order, Date start_date)
    {
        super(order, "Delivered", start_date);
       // setOrder_status_enum(Order_Status_Enum.DELIVERED);
    }
}

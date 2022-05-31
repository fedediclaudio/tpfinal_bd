package com.bd.tpfinal.model;

import java.util.Date;
import javax.persistence.Entity;

@Entity
public class Delivered extends OrderStatus{
    public Delivered(){}


    public Delivered(Order order ){ super (order,  "Delivered");}
    public Delivered(Order order, Date startDate ){ super (order, "Delivered", startDate);}

}

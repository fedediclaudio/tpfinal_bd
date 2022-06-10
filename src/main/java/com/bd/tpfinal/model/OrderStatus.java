package com.bd.tpfinal.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "OrderStatus_Type")
//@Table(name = "ordersStatus")
//@Embeddable  para declarar que una clase será incrustada por otras entidades.
@Embeddable
public class OrderStatus
{

    //@Column(name = "state_name")
    protected String name;

    //@Column(name = "state_start_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "es_AR")
    protected Date startDate;

    //@Transient
    @OneToOne
    @JoinColumn(name = "number")
    @JsonIgnore
    protected Order order;

    public OrderStatus()
    {
    }

    public OrderStatus(Order order, String name, Date startDate)
    {
        setName(name);
        setStartDate(startDate);
        setOrder(order);
    }

    public OrderStatus(Order order, String name)
    {
        this.name = name;
        this.order = order;
        this.startDate = Calendar.getInstance().getTime();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public boolean canAddItem()
    {
        return false;
    }

    public boolean canAssign()
    {
        return false;
    }

    public boolean canRefuse()
    {
        return false;
    }

    public boolean canDeliver()
    {
        return false;
    }

    public boolean canFinish()
    {
        return false;
    }

    public boolean canCancel()
    {
        return false;
    }

    //TODO: se agregó parámetro.
    public boolean addItem(Item newItem) throws Exception
    {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean assign(DeliveryMan deliveryMan) throws Exception
    {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean refuse() throws Exception
    {
        throw new Exception("No se puede realizarse esta accion");
    }

    //solamente debería no lanzar exception cuando el estado
    //es assigned
    public boolean deliver() throws Exception
    {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean cancel() throws Exception
    {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean finish() throws Exception
    {
        throw new Exception("No se puede realizarse esta accion");
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }
}

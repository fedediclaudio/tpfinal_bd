package com.bd.tpfinal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.util.Date;
//@Entity
//@DiscriminatorValue("Sent")
@Embeddable
public class Sent extends OrderStatus
{

    //private Order order;

    public Sent(Order order, Date start_date)
    {
        super(order,"Sent" ,start_date);
    }

    @Override
    public boolean canAssign()
    {
        return false;
    }

    @Override
    public boolean canRefuse()
    {
        return false;
    }

    @Override
    public boolean canDeliver()
    {
        return false;
    }

    /**
     * un cliente suma un punto por cada pedido finalizado y
     * resta un punto cuando cancela uno ya confirmado y asignado;
     * un repartidor suma un punto cuando completa una entrega mientras que
     * resta dos puntos cuando rechaza un pedido que le fue asignado.
     * @return
     */
    //esto activa la actualización de puntajes, tanto para Client como para DeliveryMan
    @Override
    public boolean finish()
    {
        Date start_date = new Date();
        //OrderStatus orderStatus = Status_Factory.getInstance(Order_Status_Enum.DELIVERED, super.getOrder(),"delivered",start_date);
        getOrder().setOrderStatus(new Delivered(getOrder(), start_date));
        //super.getOrder().setOrderStatus(orderStatus);
        int scoreCliente = getOrder().getClient().getScore() + 1;
        getOrder().getClient().setScore(scoreCliente);
        int scoreDeliveryMan = getOrder().getDeliveryMan().getScore() + 1;
        getOrder().getDeliveryMan().setScore(scoreDeliveryMan);
        return true;
    }
}

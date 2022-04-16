package com.bd.tpfinal.model;

import java.util.Date;

public class Sent extends OrderStatus
{

    //private Order order;

    public Sent(Order order, String name, Date start_date)
    {
        super.setOrder(order);
        super.setName(name);
        super.setStartDate(start_date);
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
        OrderStatus orderStatus = Status_Factory.getInstance(Order_Status_Enum.DELIVERED, super.getOrder(),"delivered",start_date);
        super.getOrder().setStatus(orderStatus);
        int scoreCliente = super.getOrder().getClient().getScore() + 1;
        super.getOrder().getClient().setScore(scoreCliente);
        int scoreDeliveryMan = super.getOrder().getDeliveryMan().getScore() + 1;
        super.getOrder().getDeliveryMan().setScore(scoreDeliveryMan);
        return true;
    }
}

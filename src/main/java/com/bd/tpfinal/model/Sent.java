package com.bd.tpfinal.model;

public class Sent extends OrderStatus
{

    private Order order;

    public Sent(Order order)
    {
        this.order = order;
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
        this.order.setStatus(new Delivered(order));
        int scoreCliente = this.order.getClient().getScore() + 1;
        this.order.getClient().setScore(scoreCliente);
        int scoreDeliveryMan = this.order.getDeliveryMan().getScore() + 1;
        this.order.getDeliveryMan().setScore(scoreDeliveryMan);
        return true;
    }
}

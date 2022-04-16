package com.bd.tpfinal.model;

import java.util.Date;

public class Assigned extends OrderStatus
{
    //private Order order;

    public Assigned(Order order, String name, Date start_date)
    {
        super.setOrder(order);
        super.setName(name);
        super.setStartDate(start_date);
        setOrder_status_enum(Order_Status_Enum.ASSIGNED);
    }

    @Override
    public boolean canAssign()
    {
        return false;
    }

    //Order cancelada por el cliente
    //lo hace antes de que la Order sea aceptada por un DeliveryMan
    @Override
    public boolean cancel()
    {
        //TODO: ver eso de canCancel()
        Date start_date = new Date();
        OrderStatus orderStatus = Status_Factory.getInstance(Order_Status_Enum.CANCELLED, super.getOrder(),"cancelled",start_date);
        super.getOrder().setStatus(orderStatus);
        return true;
    }

    /**
     * Dicho repartidor puede rechazar el pedido (descontando su puntaje), en cuyo
     * caso el estado pasa a un estado de cancelado (Cancelled),
     * -- un repartidor suma un punto cuando completa una entrega mientras
     * que resta dos puntos cuando rechaza un pedido que le fue asignado.
     *
     * @return
     */
    @Override
    public boolean refuse()
    {
        boolean rta = false;
        //TODO: no entiendo para que sirve canRefuse()
        if (canRefuse())
        {
            //Cancel cancel = new Cancel(super.getOrder());
            Date start_date = new Date();
            OrderStatus orderStatus = Status_Factory.getInstance(Order_Status_Enum.CANCELLED, super.getOrder(),"cancelled",start_date);
            super.getOrder().setStatus(orderStatus);
            int score = super.getOrder().getDeliveryMan().getScore();
            super.getOrder().getDeliveryMan().setScore(score -2);
            rta = true;
        }
        return rta;
    }

    /**
     *  o puede aceptarlo y comenzar con el reparto del mismo,
     *  así este último pasa a un estado de en proceso (Sent).
     * @return
     */
    @Override
    public boolean deliver()
    {
        Date start_date = new Date();
        OrderStatus orderStatus = Status_Factory.getInstance(Order_Status_Enum.DELIVERED, super.getOrder(),"delivered",start_date);
        super.getOrder().setStatus(orderStatus);
        return true;
    }
}

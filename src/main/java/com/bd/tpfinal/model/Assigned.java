package com.bd.tpfinal.model;

import java.util.Date;

public class Assigned extends OrderStatus
{
    //private Order order;

    public Assigned(Order order, String name, Date start_date)
    {
        //super.setOrder(order);
        //this.order = order;
        //super.setOrder_status_enum(Order_Status_Enum.ASSIGNED);

        super.setOrder_status_enum(Order_Status_Enum.ASSIGNED);
        //this.order = order;
        super.setOrder(order);
        super.setName(name);
        super.setStartDate(start_date);
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
        Cancel cancel = new Cancel(super.getOrder());
        cancel.setCancelledByClient(true);
        super.getOrder().setStatus(cancel);
        int score_cliente = super.getOrder().getClient().getScore() - 1;
        super.getOrder().getClient().setScore(score_cliente);
        return true;

        Date start_date = new Date();
        OrderStatus orderStatus = Status_Factory.getInstance(Order_Status_Enum.ASSIGNED, super.getOrder(),"assign",start_date);
        //super.getOrder().setStatus(new Assigned(super.getOrder()));
        super.getOrder().setStatus(orderStatus);
        super.getOrder().setDeliveryMan(deliveryMan);
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
            Cancel cancel = new Cancel(super.getOrder());
            cancel.setCancelledByClient(false);
            super.getOrder().setStatus(new Cancel(super.getOrder()));
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
        super.getOrder().setStatus(new Sent(super.getOrder()));
        return true;
    }
}

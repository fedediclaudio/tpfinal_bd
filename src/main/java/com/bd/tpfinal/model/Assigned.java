package com.bd.tpfinal.model;

import javax.persistence.Embeddable;
import java.util.Date;
//@Entity
//@DiscriminatorValue("Assigned")
@Embeddable
public class Assigned extends OrderStatus
{
    //private Order order;

    public Assigned(Order order, Date start_date)
    {
        super(order, "Assigned", start_date);
       // setOrder_status_enum(Order_Status_Enum.ASSIGNED);
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
        //OrderStatus orderStatus = Status_Factory.getInstance(Order_Status_Enum.CANCELLED, super.getOrder(),"cancelled",start_date);
        getOrder().setOrderStatus(new Cancelled(getOrder(), start_date));
        return true;
    }

    /**
     * Dicho repartidor puede rechazar el pedido (descontando su puntaje), en cuyo
     * caso el estado pasa a un estado de cancelado (Cancelled),
     * -- un repartidor suma un punto cuando completa una entrega mientras
     * que resta dos puntos cuando rechaza un pedido que le fue asignado.
     * el DeliveryMan asigando vuelve a estar free
     *
     * @return
     */
    @Override
    public boolean refuse()
    {
        boolean rta = false;
        //TODO: no entiendo para que sirve canRefuse(), si el patron state hace todo
        Date start_date = new Date();
        getOrder().setOrderStatus(new Cancelled(getOrder(), start_date));
        int score = super.getOrder().getDeliveryMan().getScore();
        super.getOrder().getDeliveryMan().setScore(score -2);
        super.getOrder().getDeliveryMan().setFree(true);
        return true;
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
        //OrderStatus orderStatus = Status_Factory.getInstance(Order_Status_Enum.DELIVERED, super.getOrder(),"delivered",start_date);
        super.getOrder().setOrderStatus(new Sent(getOrder(), start_date));
        return true;
    }
}

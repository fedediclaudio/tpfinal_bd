package com.bd.tpfinal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Embeddable
public class Pending extends OrderStatus
{
    //private Order order;

    public Pending()
    {

    }

    public Pending(Order order, Date start_date)
    {
        super(order, "Pending", start_date);
    }

    /**
     * Una vez confirmado por el cliente, el pedido se asigna a un
     * repartidor libre y pasa a un estado de asignado (Assigned).
     * @param deliveryMan
     * @return
     */
    @Override
    public boolean assign(DeliveryMan deliveryMan)
    {
        Date start_date = new Date();
        //getOrder().setStatusByName();
        deliveryMan.setFree(false);
        getOrder().setDeliveryMan(deliveryMan);
        OrderStatus orderStatus = new Assigned(getOrder(),start_date);
        getOrder().setOrderStatus(orderStatus);
        return true;
    }

    /**
     * Antes de que el pedido fuera aceptado por el repartidor, este
     * puede ser cancelado por el cliente en cualquier momento,
     * llevándolo a un estado de cancelado (Cancelled).
     * @return
     *
     * @Override
     *     public boolean cancel()
     *     {
     *         boolean rta = false;
     *         //TODO: ojo con canCancel() para mi no sirve
     *         if(canCancel())
     *         {
     *             Cancel cancel = new Cancel(super.getOrder());
     *             cancel.setCancelledByClient(true);
     *             super.getOrder().setStatus(cancel);
     *             rta = true;
     *         }
     *         return rta;
     *     }
     */
    @Override
    public boolean cancel()
    {
        //TODO: ver eso de canCancel()
        Date start_date = new Date();
        OrderStatus orderStatus = Status_Factory.getInstance(Order_Status_Enum.CANCELLED, super.getOrder(),"cancelled",start_date);
        super.getOrder().setOrderStatus(orderStatus);
        return true;
    }

    /**
     * No verifica repeticion de items
     * @param newItem
     * @return
     */
    //TODO: esto esta mal, debe hacerse desde el controller
    @Override
    public boolean addItem(Item newItem)
    {
        List<Item> items = this.getOrder().getItems();
        items.add(newItem);
        this.getOrder().setItems(items);
        return true;
    }
}

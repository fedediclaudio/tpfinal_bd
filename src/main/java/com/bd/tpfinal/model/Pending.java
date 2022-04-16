package com.bd.tpfinal.model;

import java.util.Date;
import java.util.List;

public class Pending extends OrderStatus
{
    //private Order order;

    public Pending(Order order, String name, Date start_date)
    {
        setName(name);
        setStartDate(start_date);
        setOrder(order);
        //setOrder_status_enum(Order_Status_Enum.PENDING);
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
        OrderStatus orderStatus = Status_Factory.getInstance(Order_Status_Enum.ASSIGNED, super.getOrder(),"assign",start_date);
        getOrder().setStatus(orderStatus);
        super.getOrder().setDeliveryMan(deliveryMan);
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
        super.getOrder().setStatus(orderStatus);
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

package com.bd.tpfinal.model;

import java.util.List;

public class Pending extends OrderStatus
{
    //private Order order;

    public Pending(Order order)
    {
        super.setOrder_status_enum(Order_Status_Enum.PENDING);
        //this.order = order;
        super.setOrder(order);
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
        super.getOrder().setStatus(new Assigned(super.getOrder()));
        super.getOrder().setDeliveryMan(deliveryMan);
        return true;
    }

    /**
     * Antes de que el pedido fuera aceptado por el repartidor, este
     * puede ser cancelado por el cliente en cualquier momento,
     * llevándolo a un estado de cancelado (Cancelled).
     * @return
     */
    @Override
    public boolean cancel()
    {
        boolean rta = false;
        //TODO: ojo con canCancel() para mi no sirve
        if(canCancel())
        {
            Cancel cancel = new Cancel(super.getOrder());
            cancel.setCancelledByClient(true);
            super.getOrder().setStatus(cancel);
            rta = true;
        }
        return rta;
    }

    /**
     * No verifica repeticion de items
     * @param newItem
     * @return
     */
    //TODO: no verifica repetición de items
    @Override
    public boolean addItem(Item newItem)
    {
        List<Item> items = this.getOrder().getItems();
        items.add(newItem);
        this.getOrder().setItems(items);
        return true;
    }
}

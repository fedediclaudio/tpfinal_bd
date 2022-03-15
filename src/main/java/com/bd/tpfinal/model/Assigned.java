package com.bd.tpfinal.model;

public class Assigned extends OrderStatus
{
    private Order order;

    public Assigned(Order order)
    {
        this.order = order;
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
        Cancel cancel = new Cancel(order);
        cancel.setCancelledByClient(true);
        this.order.setStatus(cancel);
        int score_cliente = this.order.getClient().getScore() - 1;
        this.order.getClient().setScore(score_cliente);
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
            Cancel cancel = new Cancel(order);
            cancel.setCancelledByClient(false);
            this.order.setStatus(new Cancel(order));
            int score = this.order.getDeliveryMan().getScore();
            this.order.getDeliveryMan().setScore(score -2);
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
        this.order.setStatus(new Sent(order));
        return true;
    }
}

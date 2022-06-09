package com.bd.tpfinal.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Pending extends OrderStatus{
    public Pending(){}
    public Pending(Order order ){ super (order,  "Pending");}
    public Pending(Order order, Date startDate ){ super (order, "Pending", startDate);}

    @Override
    public boolean canAssign(){ return true;}

    @Override
    public boolean canCancel (){return true;}

    @Override
    public boolean assign(DeliveryMan deliveryMan) throws Exception {
        if (!deliveryMan.isFree())
            throw new Exception("El deliveryman no está libre ");
        deliveryMan.addOrder(this.getOrder()); // se agrega la orden al repartidor
        deliveryMan.setFree(false); // el repartidor ahora esta ocupado
        this.getOrder().setDeliveryMan(deliveryMan);// Se asigna el repartidor a la Orden
        this.getOrder().setStatus(new Assigned(this.getOrder(), new Date()));// Se pasa la Orden a estado de Asignado
        return true;
    }

    @Override
    public boolean cancel() throws Exception {// La orden no esta asignada, es cancelada por el cliente y no resta puntos
        this.getOrder().setStatus(new Cancel(this.getOrder(), new Date())); // la orden queda cancelada
        ((Cancel)this.getOrder().getStatus()).setCancelledByClient(true); // cancelada por cliente, ver si se puede hacer en cancelorder
        return true;



    }

}

package com.bd.tpfinal.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Assigned extends OrderStatus{
    public Assigned(){}

    public Assigned(Order order ){ super (order,  "Assigned");}
    public Assigned(Order order, Date startDate ){ super (order, "Assigned", startDate);}


    public boolean canRefuse() {
        return true;
    }

    public boolean canDeliver() {
        return true;
    }

    public boolean canCancel() {
        return true;
    }

    public boolean deliver() throws Exception { // el repartidor acepta el pedido y comienza el reparto
            this.getOrder().setStatus(new Sent(this.getOrder())); // la orden se envía
            this.getOrder().getDeliveryMan().deleteOrder(getOrder()); //Remuevo la orden de la lista de pendientes de envío del DeliveryMan
            return true;
    }

    public boolean refuse() throws Exception { // recahazado por repartidor
            this.getOrder().setStatus(new Cancel(this.getOrder())); // la orden queda cancelada
            this.getOrder().getDeliveryMan().decreaseScore(); // descuenta 2 puntos del repartidor
            this.getOrder().getDeliveryMan().deleteOrder(getOrder()); //Remuevo la orden de la lista de pendientes de envío del DeliveryMan
            this.getOrder().getDeliveryMan().setFree(true); // ahora está libre
            ((Cancel)this.getOrder().getStatus()).setCancelledByClient(false); // no es cancelada por cliente, fue rechazada
            return true;
    }

    public boolean cancel() throws Exception { //cancelado por cliente
            this.getOrder().setStatus(new Cancel(this.getOrder())); // la orden queda cancelada
            this.getOrder().getDeliveryMan().deleteOrder(getOrder()); //Remuevo la orden de la lista de pendientes de envío del DeliveryMan
            this.getOrder().getClient().decreaseScore(); // descuenta un punto del cliente
            this.getOrder().getDeliveryMan().setFree(true); // el repartidor está libre
            ((Cancel)this.getOrder().getStatus()).setCancelledByClient(true); // cancelada por cliente, ver si se puede hacer en cancelorder
            return true;
    }

}

package com.bd.tpfinal.model;

import java.util.Date;
import javax.persistence.Entity;

@Entity
public class Sent extends OrderStatus{

    public Sent(){}

    public Sent(Order order ){ super (order,  "Sent");}
    public Sent(Order order, Date startDate ){ super (order, "Sent", startDate);}


    @Override
    public boolean canFinish() {
        return true;
    }
    @Override
    public boolean finish(int score, String commentary) throws Exception { // la orden fue entregrada, finalizar pedido. Se actualizan los puntajes del cliente y del DeliveryMan
        this.getOrder().getDeliveryMan().increaseScore(); // agrega puntaje al repartidor
        this.getOrder().getClient().increaseScore(); //agrega puntaje al cliente
        this.getOrder().getDeliveryMan().setFree(true); //queda libre el repartidor
        this.getOrder().getDeliveryMan().addNumberOfSuccessfulOrders(); // incrementa las ordenes finalizadas del repartidor
        this.getOrder().setStatus( new Delivered( this.getOrder()) ); // Se pasa la Orden a estado de Entregado
        this.getOrder().setQualification(new Qualification(score, commentary)); // califica la operación
        return true;
    }
}

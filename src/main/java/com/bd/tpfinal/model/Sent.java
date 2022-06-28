package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
public class Sent extends OrderStatus{
    public Sent(){}

    public Sent(Order order ){ super(order,  "Sent");}
    public Sent(Order order, Date startDate ){ super(order, "Sent", startDate);}


    @Override
    public boolean canFinish() {
        return true;
    }

    public boolean finish(int score, String commentary) throws Exception { // la orden fue entregrada, finalizar pedido. Se actualizan los puntajes del cliente y del DeliveryMan
        this.getOrder().getDeliveryMan().increaseScore(); // agrega puntaje al repartidor
        this.getOrder().getClient().increaseScore(); //agrega puntaje al cliente
        this.getOrder().getDeliveryMan().setFree(true); //queda libre el repartidor
        this.getOrder().getDeliveryMan().addNumberOfSuccessfulOrders(); // incrementa las ordenes finalizadas del repartidor
        this.getOrder().setStatus( new Delivered( this.getOrder()) ); // Se pasa la Orden a estado de Entregado
        this.getOrder().setQualification(new Qualification(this.getOrder(), score, commentary)); // califica la operación
        // this.getOrder().updateQualification(new Qualification(score, commentary)); // califica la operación y ver si puedo hacer calificar al supplier

        return true;
    }

    /*
    @Override
    public boolean finish(int score, String commentary) throws Exception { // la orden fue entregrada, finalizar pedido. Se actualizan los puntajes del cliente y del DeliveryMan
        this.getOrder().getDeliveryMan().increaseScore(); // agrega puntaje al repartidor
        this.getOrder().getClient().increaseScore(); //agrega puntaje al cliente
        this.getOrder().getDeliveryMan().setFree(true); //queda libre el repartidor
        this.getOrder().getDeliveryMan().addNumberOfSuccessfulOrders(); // incrementa las ordenes finalizadas del repartidor
        this.getOrder().setStatus( new Delivered( this.getOrder()) ); // Se pasa la Orden a estado de Entregado
        //this.getOrder().setQualification(new Qualification(score, commentary)); // califica la operación
        this.getOrder().updateQualification(new Qualification(score, commentary)); // califica la operación y ver si puedo hacer calificar al supplier

        return true;
    }*/
}

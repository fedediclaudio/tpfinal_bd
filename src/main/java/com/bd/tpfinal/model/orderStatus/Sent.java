package com.bd.tpfinal.model.orderStatus;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document(collection = "Sent")
public class Sent extends OrderStatus {

    public Sent() {}

    public Sent(Order order) {
        super("Sent", LocalDate.now(), order);
    }

    @Override
    public boolean canFinish() {
        return true;
    }

    @Override
    public boolean finish() throws Exception {
        // Se actualizan los puntajes del cliente y del DeliveryMan
        this.getOrder().addClientScore();
        this.getOrder().addDeliveryManScore();

        // Configuro al DeliveryMan como libre
        this.getOrder().setDeliveryManBusyTo(false);

        // Se pasa la Orden a estado de Entregado
        this.getOrder().setStatus( new Delivered( this.getOrder()) );

        return true;
    }

}
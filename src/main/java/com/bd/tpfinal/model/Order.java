package com.bd.tpfinal.model;

import com.bd.tpfinal.model.orderStatus.Cancel;
import com.bd.tpfinal.model.orderStatus.Delivered;
import com.bd.tpfinal.model.orderStatus.Pending;
import com.bd.tpfinal.model.orderStatus.Sent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Orders")
public class Order {

    //    @MongoId(value = FieldType.OBJECT_ID)
    private String number;

    private LocalDate dateOfOrder;

    private String comments;

    private float totalPrice;

    private OrderStatus status;

    @DocumentReference
    private DeliveryMan deliveryMan;

    @DocumentReference
    private Client client;

    private Address address;

    private Qualification qualification;

//    @DBRef
    private List<Item> items;

    @Version
    private int version;


    public Order(Client client) {
        // Por defecto la orden esta en Pendiente
        this.status = new Pending(this);
        this.dateOfOrder = LocalDate.now();
        this.client = client;

        // Inicializo la lista de Items
        this.items = new ArrayList<>();
    }



    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order [number=" + number + ", dateOfOrder=" + dateOfOrder + ", comments=" + comments + ", totalPrice="
                + totalPrice + ", status=" + status + ", deliveryMan=" + deliveryMan + ", client=" + client
                + ", address=" + address + ", qualification=" + qualification + ", items=" + items + ", version="
                + version + "]";
    }

    /////////////////////////
    // Metodos adicionales //
    /////////////////////////

    public boolean addItem(Item item) throws Exception {
        if (this.status.canAddItem()) {
            this.items.add(item);
            return true;
        }
        return false;
    }

    //////////////////////////////////////
    // Cambio de estado de las ordenes  //
    //////////////////////////////////////
    public void cancelOrder() throws Exception {
        this.status = new Cancel(this);
    }

    public void refuseOrder() throws Exception {
        this.status = new Cancel(this);
    }

    public void deliverOrder() throws Exception {
        this.status = new Sent(this);
    }

    public void finishOrder() throws Exception {
        this.status = new Delivered(this);
    }

    //////////////////////////////////////
    // FIN Cambio de estado de las ordenes  //
    //////////////////////////////////////


    public void deductClientScore() throws Exception {
        this.client.deductScore();
    }

    public void addClientScore() throws Exception {
        this.client.addScore();
    }

    public void setDeliveryManBusyTo(boolean isBusy) {
        this.deliveryMan.setFree(!isBusy);
    }

    public void deductDeliveryManScore() throws Exception {
        this.deliveryMan.deductScore();
    }

    public void addDeliveryManScore() throws Exception {
        this.deliveryMan.addScore();
    }

}

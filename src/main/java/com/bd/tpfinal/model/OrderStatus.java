package com.bd.tpfinal.model;

import com.bd.tpfinal.annotation.CascadePersist;
import com.bd.tpfinal.repositories.OrderStatusRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.Date;

@Data
@Document(collection = "orderStatus")
public class OrderStatus {

    @Id
    private String Id;

    private String name;

    private Date startDate;

    @DBRef
    @JsonBackReference("order")
    @ToString.Exclude
    private Order order;

    public OrderStatus() {
    }

    public OrderStatus(Order order, String name) {
        this.order = order;
        this.name = name;
        this.startDate = Calendar.getInstance().getTime();
    }

    public OrderStatus(Order order, String name, Date startDate) {
        this.order = order;
        this.name = name;
        this.startDate = startDate;
    }

    public OrderStatus(String currentId, Order order, String name, Date startDate) {
        this.order = order;
        this.name = name;
        this.startDate = startDate;
        this.setId(currentId);
    }

    public boolean canAddItem() {
        return false;
    }

    public boolean canAssign() {
        return false;
    }

    public boolean canRefuse() {
        return false;
    }

    public boolean canDeliver() {
        return false;
    }

    public boolean canFinish() {
        return false;
    }

    public boolean canCancel() {
        return false;
    }

    public boolean addItem() throws Exception {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean assign(DeliveryMan deliveryMan) throws Exception {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean refuse() throws Exception {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean deliver() throws Exception {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean cancel() throws Exception {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean finish(int score, String commentary) throws Exception{
        throw new Exception("No se puede realizarse esta accion");
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

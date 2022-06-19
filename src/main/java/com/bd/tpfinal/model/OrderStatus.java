package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Transient;

import java.util.Date;

public abstract class OrderStatus extends PersistentEntity {

    private String name;
    @JsonProperty("start_date")
    private Date startDate = new Date();
    @Transient
    private Order order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public boolean canAddItem() { return false; }

    public boolean canAssign() { return false; }

    public boolean canRefuse() { return false; }

    public boolean canDeliver() { return false; }

    public boolean canFinish() { return false; }

    public boolean canCancel() { return false; }

    public boolean addItem() throws Exception{
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean assign(DeliveryMan deliveryMan) throws Exception{
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean refuse() throws Exception{
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean deliver() throws Exception{
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean cancel() throws Exception{
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean finish() throws Exception{
        throw new Exception("No se puede realizarse esta accion");
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setName(){
        this.setName("name");
    }
}

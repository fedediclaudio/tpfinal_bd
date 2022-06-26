package com.bd.tpfinal.model;

import lombok.Data;

import java.util.Calendar;
import java.util.Date;

@Data
public abstract class OrderStatus {

    private String name;

    private Date startDate;

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

    public boolean finish() throws Exception {
        throw new Exception("No se puede realizarse esta accion");
    }
}

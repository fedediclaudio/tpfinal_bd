package com.bd.tpfinal.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class DeliveryMan extends User{

    private int numberOfSuccessOrders;

    private boolean free;

    private Date dateOfAdmission;

    private List<Order> ordersPending;

}

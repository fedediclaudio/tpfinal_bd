package com.bd.tpfinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("DeliveryMan")
public class DeliveryMan extends User {

    private int numberOfSuccessOrders;

    private boolean free;

    private Date dateOfAdmission;

    private List<Order> ordersPending;

}

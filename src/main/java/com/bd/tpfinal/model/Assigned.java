package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "Assigned")
public class Assigned extends OrderStatus {

    public Assigned(Order order, Date startDate) {
        super(order, "Assigned", startDate);
    }

    public Assigned(String id, Order order, Date date) {
        super(id, order, "Assigned", date);
    }
}

package com.bd.tpfinal.model;

import com.bd.tpfinal.annotation.CascadePersist;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


@Data
public class Item {

    @Id
    private String id;

    private int quantity;

    private String description;

    @DBRef
    @CascadePersist
    private Order order;

    @DBRef
    @CascadePersist
    private Product product;
}

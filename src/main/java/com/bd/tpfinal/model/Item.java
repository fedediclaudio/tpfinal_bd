package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    private String id;

    private int quantity;

    private String description;

    @DBRef(lazy = true)
    @JsonBackReference
    private Order order;

    private Product product;

}

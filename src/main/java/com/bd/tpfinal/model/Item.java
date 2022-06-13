package com.bd.tpfinal.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Item")
public class Item {

    private String id;

    private int quantity;

    private String description;

    //	@DocumentReference
    @DBRef(lazy = true)
    private Order order;

    //	@DocumentReference
//	@DBRef
    private Product product;

}

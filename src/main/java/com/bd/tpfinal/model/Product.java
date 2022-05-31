package com.bd.tpfinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Product")
public class Product {

    @Id
    private ObjectId id;

    private String name;

    private float price;

    private float weight;

    private String description;

    @DBRef
    private Supplier supplier;

    @DBRef
    private ProductType type;

    private List<HistoricalProductPrice> prices;

}

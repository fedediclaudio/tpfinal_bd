package com.bd.tpfinal.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document
public class Product {

    @MongoId
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

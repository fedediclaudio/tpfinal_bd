package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Product {

    @Id
    private String Id;

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

package com.bd.tpfinal.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Product")
@Getter
@Setter
public class Product {

    @Id
    private String id;

    private String name;

    private float price;

    private float weight;

    private String description;

    private Supplier supplier;

    private ProductType type;

    @DBRef(lazy = true)
    private List<HistoricalProductPrice> prices;

    private boolean productDeleted;


    public Product() {
        this.prices = new ArrayList<HistoricalProductPrice>();
        this.productDeleted = false;
    }


    public void addHistoricalPrice(HistoricalProductPrice historical) {
        this.prices.add(historical);
    }

    public boolean isProductDeleted() {
        return productDeleted;
    }
}

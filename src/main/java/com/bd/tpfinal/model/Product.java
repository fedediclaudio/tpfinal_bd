package com.bd.tpfinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Product")
public class Product {

    private String id;

    private String name;

    private float price;

    private float weight;

    private String description;

    private Supplier supplier;

    private ProductType type;

    private List<HistoricalProductPrice> prices;

    private boolean productDeleted;

    public void addHistoricalPrice(HistoricalProductPrice historical) {
        this.prices.add(historical);
    }
}

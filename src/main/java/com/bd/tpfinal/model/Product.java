package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Product {

    @Id
    private String Id;

    private String name;

    private float price;

    private float weight;

    private String description;

    private Supplier supplier;

    private ProductType type;

    private List<HistoricalProductPrice> prices;
}

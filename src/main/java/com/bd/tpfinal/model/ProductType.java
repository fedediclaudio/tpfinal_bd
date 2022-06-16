package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "ProductType")
@Getter
@Setter
public class ProductType {

    @Id
    private String id;
    private String name;
    private String description;

    @DBRef(lazy = true)
    @DocumentReference
    @JsonBackReference
    private List<Product> products;

    private Double avgProductsPrice;

    public ProductType() {
        this.products = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }


}

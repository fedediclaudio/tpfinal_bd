package com.bd.tpfinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ProductType")
public class ProductType {

    private String id;
    private String name;
    private String description;

    @DBRef(lazy = true)
    @DocumentReference
    private List<Product> products;

    private Double avgProductsPrice;

    public void addProduct(Product product) {
        this.products.add(product);
    }

}

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
@Document(collection = "Supplier")
public class Supplier {

    private String id;

    private String name;

    private String cuil;

    private String address;

    private float[] coords;

    private float qualificationOfUsers;

    @DBRef(lazy = true)
    @DocumentReference
    private List<Product> products;

    private SupplierType type;

    public void addProductt(Product product) {
        this.products.add(product);
    }
}

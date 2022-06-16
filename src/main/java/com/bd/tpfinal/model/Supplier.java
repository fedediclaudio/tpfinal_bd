package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Document(collection = "Supplier")
@Data
@AllArgsConstructor
public class Supplier {

    @Id
    private String id;

    private String name;

    private String cuil;

    private String address;

    private float[] coords;

    private float qualificationOfUsers;

    @DBRef(lazy = true)
    @JsonBackReference(value = "products")
    private List<Product> products;

    private SupplierType type;


    public Supplier() {
        this.products = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }


    @Override
    public String toString() {
        return "Supplier [id=" + id + ", name=" + name + ", cuil=" + cuil + ", address=" + address + ", coords="
                + Arrays.toString(coords) + ", qualificationOfUsers=" + qualificationOfUsers + ", products=" + products
                + ", type=" + type + "]";
    }

}

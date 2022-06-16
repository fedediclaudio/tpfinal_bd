package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "SupplierType")
@Data
@AllArgsConstructor
public class SupplierType {

    @Id
    private String id;

    private String name;

    private String description;

    @DBRef(lazy = true)
    @JsonBackReference(value = "suppliers")
    private List<Supplier> suppliers;


    public SupplierType() {
        this.suppliers = new ArrayList<Supplier>();
    }

    public void addSupplierToList(Supplier supplier) {
        this.suppliers.add(supplier);
    }

}

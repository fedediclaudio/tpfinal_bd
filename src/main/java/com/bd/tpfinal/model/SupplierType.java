package com.bd.tpfinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "SupplierType")
public class SupplierType {

    private String id;

    private String name;

    private String description;

    @DBRef(lazy = true)
    private List<Supplier> suppliers;

    public void addSupplierToList(Supplier supplier) {
        supplier.setType(null);
        this.suppliers.add(supplier);
    }
}

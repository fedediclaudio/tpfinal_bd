package com.bd.tpfinal.model;

import com.bd.tpfinal.annotation.CascadePersist;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Supplier {
    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String cuil;

    private String address;

    private float[] coords;

    private float qualificationOfUsers;
    @DBRef
    @JsonBackReference("products")
    private List<Product> products;

    @DBRef
    @CascadePersist
    private SupplierType type;
}

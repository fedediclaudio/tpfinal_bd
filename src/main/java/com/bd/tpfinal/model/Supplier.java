package com.bd.tpfinal.model;

import com.bd.tpfinal.annotation.CascadePersist;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Document
public class Supplier {
    @Id
    private ObjectId id;

    private String name;

    private String cuil;

    private String address;

    private float[] coords;

    private float qualificationOfUsers;
    @DBRef
    private List<Product> products;

    @DBRef
    @CascadePersist
    private SupplierType type;
}

package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document
public class ProductType {
    @Id
    private String id;

    private String name;

    private String description;
    @DBRef
    @JsonBackReference
    private List<Product> products;
}

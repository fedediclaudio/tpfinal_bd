package com.bd.tpfinal.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document
public class ProductType {
    @MongoId
    private ObjectId id;
    private String name;

    private String description;
    @DBRef
    private List<Product> products;
}

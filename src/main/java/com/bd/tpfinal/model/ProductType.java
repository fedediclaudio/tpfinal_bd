package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class ProductType {
    @Id
    private String id;
    private String name;

    private String description;

    private List<Product> products;


}

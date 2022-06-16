package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Qualification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Qualification {

    @Id
    private String id;

    private float score;

    private String commentary;

    @DBRef(lazy = true)
    @JsonBackReference
    private Order order;

}

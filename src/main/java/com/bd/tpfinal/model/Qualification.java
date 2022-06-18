package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Qualification {
    @Id
    private String id;
    private float score;

    private String commentary;
    @DBRef
    private Order order;

    public Qualification(float score, String commentary) {
        this.score = score;
        this.commentary = commentary;
    }
}

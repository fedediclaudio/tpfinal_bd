package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@Document
public class Qualification {
    @Id
    private float score;

    private String commentary;
    @DocumentReference
    private Order order;

    public Qualification(float score, String commentary ){
        this.score = score;
        this.commentary = commentary;
    }
}

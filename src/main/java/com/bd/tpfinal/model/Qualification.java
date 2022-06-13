package com.bd.tpfinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Qualification")
public class Qualification {

    private String id;

    private float score;

    private String commentary;

    @DocumentReference
    private Order order;

}

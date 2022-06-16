package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

@Data
public class HistoricalProductPrice {
    @Id
    private String id;
    private float price;

    private Date startDate;

    private Date finishDate;
@DBRef
@JsonBackReference
    private Product product;

}

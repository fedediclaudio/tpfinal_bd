package com.bd.tpfinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalProductPrice {

    private float price;

    private LocalDate startDate;

    private LocalDate finishDate;

    @DBRef(lazy = true)
    private Product product;


    public HistoricalProductPrice(Product product) {
    }
}

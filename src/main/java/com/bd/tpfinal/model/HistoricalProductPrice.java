package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalProductPrice {

    @Id
    private String id;

    private float price;

    private LocalDate startDate;

    private LocalDate finishDate;

    @DBRef(lazy = true)
    @JsonBackReference(value = "product")
    private Product product;

    public HistoricalProductPrice(Product product) {
        this.price = product.getPrice();
        this.startDate = LocalDate.now();
        this.product = product;
        this.finishDate = null;
    }
}

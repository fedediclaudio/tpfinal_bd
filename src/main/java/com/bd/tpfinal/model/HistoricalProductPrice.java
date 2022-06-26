package com.bd.tpfinal.model;

import com.bd.tpfinal.annotation.CascadePersist;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
@Data
public class HistoricalProductPrice {
    public HistoricalProductPrice(Product product) {
        this.product = product;
        this.price = product.getPrice();
        this.startDate = Calendar.getInstance().getTime();;
        this.finishDate = null;
    }
    @Id
    private String id;
    private float price;
    private Date startDate;
    private Date finishDate;
    @DBRef(lazy = true)
    @JsonBackReference(value = "product")
    @CascadePersist
    @ToString.Exclude
    private Product product;
}

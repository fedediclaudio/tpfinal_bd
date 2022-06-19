package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;
public class HistoricalProductPrice extends PersistentEntity {

    private float price;
    @JsonProperty("start_date")
    private Date startDate;

    @JsonProperty("finish_date")
    private Date finishDate;
    @DBRef(lazy = true)
    private Product product;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}

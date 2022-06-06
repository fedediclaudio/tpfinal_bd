package com.bd.tpfinal.model;

import lombok.Data;

import java.util.Date;

@Data
public class HistoricalProductPrice {

    private float price;

    private Date startDate;

    private Date finishDate;

    private Product product;

}

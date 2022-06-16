package com.bd.tpfinal.model.dto;

public class ProductAvgDTO {

    private String id;
    private Float average;

    public ProductAvgDTO(String id, Float avg) {
        this.id = id;
        this.average = avg;
    }

    public ProductAvgDTO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }
}

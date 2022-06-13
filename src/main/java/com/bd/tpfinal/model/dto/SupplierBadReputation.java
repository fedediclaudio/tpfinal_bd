package com.bd.tpfinal.model.dto;


public class SupplierBadReputation {

    String name;
    float stars;
    Integer amountOfBadReputations;

    public SupplierBadReputation(String name, float stars, Integer amountOfBadReputations) {
        this.name = name;
        this.stars = stars;
        this.amountOfBadReputations = amountOfBadReputations;
    }
}

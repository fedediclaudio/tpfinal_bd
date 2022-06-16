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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public Integer getAmountOfBadReputations() {
        return amountOfBadReputations;
    }

    public void setAmountOfBadReputations(Integer amountOfBadReputations) {
        this.amountOfBadReputations = amountOfBadReputations;
    }
}

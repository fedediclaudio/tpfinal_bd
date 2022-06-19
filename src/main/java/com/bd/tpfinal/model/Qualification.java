package com.bd.tpfinal.model;

import org.springframework.data.annotation.Transient;


public class Qualification extends PersistentEntity {

    private float score;

    private String commentary;
    @Transient
    private Order order;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

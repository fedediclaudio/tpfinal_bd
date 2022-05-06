package com.bd.tpfinal.model;

import javax.persistence.*;

@Entity
public class Qualification {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private float score;

    private String commentary;
    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn( name="id_order" )
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

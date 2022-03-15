package com.bd.tpfinal.model;

import javax.persistence.*;

@Entity
@Table(name="qualifications")
public class Qualification
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_qualification")
    private Long id;

    private float score;

    private String commentary;

    @JoinColumn(name="order_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    public float getScore()
    {
        return score;
    }

    public void setScore(float score)
    {
        this.score = score;
    }

    public String getCommentary()
    {
        return commentary;
    }

    public void setCommentary(String commentary)
    {
        this.commentary = commentary;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }
}

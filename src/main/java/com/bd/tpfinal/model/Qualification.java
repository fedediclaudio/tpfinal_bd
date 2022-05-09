package com.bd.tpfinal.model;

import javax.persistence.*;

//@Entity
//@Table(name="qualifications")
@Embeddable
public class Qualification
{
    //@Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
   // @Column(name = "id_qualification")
    //private Long id;

    /**
     * Puntaje otorgado por el Cliente, de una a cinco estrellas.
     */
    private float score;

    private String commentary;

    //@JoinColumn(name="order_id")
    //@OneToOne(fetch = FetchType.LAZY)
    @Transient
    private Order order;

    public Qualification()
    {
    }

    public Qualification(Order order)
    {
        this.order = order;
        this.score = 0.0F;
        this.commentary = "creación de una calificación";
    }

    /**
     * Pre:
     * score entre 1 y 5.
     * Lo dejo como precondición con asertos que se activan con java -ea en la etapa de desarrollo.
     * @param score
     * @param commentary
     * @param order
     */
    public Qualification(float score, String commentary, Order order)
    {
        assert score >=0: "score negativo";
        assert score <=5: "score mayor que 5";
        this.score = score;
        this.commentary = commentary;
        this.order = order;
    }

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

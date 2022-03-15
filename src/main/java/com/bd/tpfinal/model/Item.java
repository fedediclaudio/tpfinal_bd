package com.bd.tpfinal.model;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_item")
    private Long id;

    private int quantity;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "id_order", nullable = false)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    //@JoinColumn(name = "product_id", referencedColumnName = "id_product")
    //Product es el padre de la asociación
    private Product product;

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }
}

package com.bd.tpfinal.model;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item
{
    @Version
    @Column(name = "OBJ_VERSION")
    private int version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_item")
    private Long id;

    private int quantity;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "id_order_number", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    //@MapsId
    //@JoinColumn(name = "product_id", referencedColumnName = "id_product")
    //Product es el padre de la asociación
    private Product product;

    public Item()
    {
    }

    public Item(int quantity, String description, Order order, Product product)
    {
        this.quantity = quantity;
        this.description = description;
        this.order = order;
        this.product = product;
    }

    public Long getId()
    {
        return id;
    }

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

    @Override
    public String toString()
    {
        String cadena = "item ID: "+getId() + " order number: "+getOrder().getNumber() + "  product id: "+getProduct().getId() + "  Supplier: " + getProduct().getSupplier().getId();
        return cadena;
    }
}

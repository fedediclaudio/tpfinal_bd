package com.bd.tpfinal.model;

public class Order_dto
{
    private Long number;
    private Item item;
    private Product product;
    private Supplier supplier;

    public Order_dto()
    {
    }

    public Order_dto(Long number, Item item, Product product, Supplier supplier)
    {
        this.number = number;
        this.item = item;
        this.product = product;
        this.supplier = supplier;
    }

    public Long getNumber()
    {
        return number;
    }

    public void setNumber(Long number)
    {
        this.number = number;
    }

    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public Supplier getSupplier()
    {
        return supplier;
    }

    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }
}

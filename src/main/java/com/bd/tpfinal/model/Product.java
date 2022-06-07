package com.bd.tpfinal.model;

import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedNativeQuery(name = "Product.getProductosPrecioPromedioDTO",
        query= "SELECT  p.name as nombreProducto, pt.name as nombreTipoProducto, AVG(p.price) as promedioPrecio "
                + " FROM product p INNER JOIN product_product_type ptp "
                + "ON p.id = ptp.id_product INNER JOIN product_type pt ON ptp.id_product_type  = pt.id "
                + "GROUP BY p.name", resultSetMapping = "Mapping.ProductoPrecioPromedioDTO")
@SqlResultSetMapping(name="Mapping.ProductoPrecioPromedioDTO",
        classes = @ConstructorResult(targetClass = ProductoPrecioPromedioDTO.class,
                columns = {
                        @ColumnResult(name="nombreProducto", type = String.class),
                        @ColumnResult(name="nombreTipoProducto", type = String.class),
                        @ColumnResult(name="promedioPrecio", type = float.class)
                }
        )
)

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private float price;

    private float weight;

    private String description;

    @ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id")
    @JsonIgnore
    private Supplier supplier;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "product_product_type",
            joinColumns = { @JoinColumn(name = "id_product") },
            inverseJoinColumns = { @JoinColumn(name = "id_product_type") })
    @JsonIgnore
    private List<ProductType> type;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HistoricalProductPrice> prices;


    public Product() {
        this.prices = new ArrayList<>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<ProductType> getType() {
        return type;
    }

    public void setType(List<ProductType> type) {
        this.type = type;
    }

    public List<HistoricalProductPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<HistoricalProductPrice> prices) {
        this.prices = prices;
    }



}

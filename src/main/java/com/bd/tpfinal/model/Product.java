package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "products")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product")
    private Long id;

    private String name;

    private float price;

    private float weight;

    private String description;

    //relación muchos a uno con DeliveryMan
    //@JoinColumn: especificar un nombre de columna de clave externa. La clave del otro lado
    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "id_supplier") //nombre del atributo clave del otro lado
    private Supplier supplier;

    //Relacion muchos a uno con ProductType
    //Lado muchos
    //@JoinColumn: especificar un nombre de columna de clave externa. La clave del otro lado
    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "id_productType") //nombre del atributo clave del otro lado
    private ProductType type;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)//era LAZY
    @JsonIgnore
    private List<HistoricalProductPrice> prices;

    //agregado
    private int eliminado;

    public Product()
    {
    }

    public Product(String name, float price, float weight, String description, Supplier supplier, ProductType type)
    {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.description = description;
        this.supplier = supplier;
        this.type = type;
        this.prices = new ArrayList<HistoricalProductPrice>();
        this.eliminado = 0;//0 es falso
        //(float price, Date startDate, Date finishDate, Product product)
        HistoricalProductPrice hpp = new HistoricalProductPrice(this.getPrice(), Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime(), null, this );
        this.prices.add(hpp);
        //prices.add(hpp);
    }

    public int getEliminado()
    {
        return eliminado;
    }

    public void setEliminado(int eliminado)
    {
        this.eliminado = eliminado;
    }

    public void eliminar()
    {
        this.eliminado = 1;
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public float getWeight()
    {
        return weight;
    }

    public void setWeight(float weight)
    {
        this.weight = weight;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Supplier getSupplier()
    {
        return supplier;
    }

    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }

    public ProductType getType()
    {
        return type;
    }

    public void setType(ProductType type)
    {
        this.type = type;
    }

    public List<HistoricalProductPrice> getPrices()
    {
        return prices;
    }

    public void setPrices(List<HistoricalProductPrice> prices)
    {
        this.prices = prices;
    }

    /**
     * agrega un nuevo hpp.
     * actualiza la fecha de finalización del anterior
     * @param hpp
     */
    public void agregarNuevoHpp(HistoricalProductPrice hpp)
    {
        int ultimo = this.prices.size()-1;
        Date finishDate = hpp.getStartDate(); //la fecha de comienzo del ultimo es la de fin del anterior.
        prices.get(ultimo).setFinishDate(finishDate); //seteo la finishDate del ultimo hpp de la lista, antes de agregar el nuevo
        prices.add(hpp);
    }

    @Override
    public String toString()
    {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", description='" + description + '\'' +
                ", supplier=" + supplier +
                ", type=" + type +
                ", prices=" + prices +
                ", eliminado=" + eliminado +
                '}';
    }
}

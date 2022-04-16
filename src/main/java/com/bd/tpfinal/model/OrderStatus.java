package com.bd.tpfinal.model;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ordersStatus")
//public abstract class OrderStatus
public class OrderStatus
{
    //TODO: solucionar el tema del patrón STATE.
    //TODO: no persiste subclases de la forma en que lo hice
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "es_AR")
    private Date startDate;

    //@JoinColumn(name = "order_id")
    //"order_id" es la columna de la tabla ordersStatus que tiene la clave foranea
    //@OneToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @OneToOne(mappedBy = "status", cascade = CascadeType.ALL)
    private Order order;


    ////// AGREGADO
    //public static enum Status {PENDING, ASSIGNED, CANCELLED, SENT, DELIVERED};
    //public static Order_Status order_status
    @Enumerated(EnumType.STRING)
    protected Order_Status_Enum order_status_enum;
    //////  FIN AGREGADO


    public OrderStatus()
    {
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

    public Date getStartDate()
    {
        return startDate;
    }

    public Order_Status_Enum getOrder_status_enum()
    {
        return order_status_enum;
    }

    public void setOrder_status_enum(Order_Status_Enum order_status_enum)
    {
        this.order_status_enum = order_status_enum;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public boolean canAddItem()
    {
        return false;
    }

    public boolean canAssign()
    {
        return false;
    }

    public boolean canRefuse()
    {
        return false;
    }

    public boolean canDeliver()
    {
        return false;
    }

    public boolean canFinish()
    {
        return false;
    }

    public boolean canCancel()
    {
        return false;
    }

    //TODO: se agregó parámetro.
    public boolean addItem(Item newItem) throws Exception
    {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean assign(DeliveryMan deliveryMan) throws Exception
    {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean refuse() throws Exception
    {
        throw new Exception("No se puede realizarse esta accion");
    }

    //solamente debería no lanzar exception cuando el estado
    //es assigned
    public boolean deliver() throws Exception
    {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean cancel() throws Exception
    {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean finish() throws Exception
    {
        throw new Exception("No se puede realizarse esta accion");
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

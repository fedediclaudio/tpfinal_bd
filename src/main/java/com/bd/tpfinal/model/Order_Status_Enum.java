package com.bd.tpfinal.model;

/**
 * enumerado que se utiliza para identificar el estdo de una Order
 * Intento poder persistir y utilizar esta información
 * para reconstruir al hijo de OrderStatus
 */
public enum Order_Status_Enum
{
    PENDING("Pending"), ASSIGNED("Assigned"),
    CANCELLED("Cancelled"), SENT("Sent"), DELIVERED("Delivered");

    private String nombre;

    private Order_Status_Enum(String nombre)
    {
        this.nombre = nombre;
    }

    public String getNombre()
    {
        return nombre;
    }
}

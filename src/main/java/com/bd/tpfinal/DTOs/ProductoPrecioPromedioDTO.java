package com.bd.tpfinal.DTOs;

public class ProductoPrecioPromedioDTO {

    public ProductoPrecioPromedioDTO() {}

    public ProductoPrecioPromedioDTO(String nombreProducto, String nombreTipoProducto, float promedioPrecio) {
        this.setNombreProducto(nombreProducto);
        this.setNombreTipoProducto(nombreTipoProducto);
        this.setPromedioPrecio(promedioPrecio);
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNombreTipoProducto() {
        return nombreTipoProducto;
    }

    public void setNombreTipoProducto(String nombreTipoProducto) {
        this.nombreTipoProducto = nombreTipoProducto;
    }

    public float getPromedioPrecio() {
        return promedioPrecio;
    }

    public void setPromedioPrecio(float promedioPrecio) {
        this.promedioPrecio = promedioPrecio;
    }

    private String nombreProducto;
    private String nombreTipoProducto;
    private float promedioPrecio;
}

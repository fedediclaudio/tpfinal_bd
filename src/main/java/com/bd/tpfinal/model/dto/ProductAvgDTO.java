package com.bd.tpfinal.model.dto;

public class ProductAvgDTO {

    private String id;
    private String nombre;
    private Float average;

    public ProductAvgDTO(String id, String nombre, Float avg) {
        this.id = id;
        this.nombre = nombre;
        this.average = avg;
    }

    public ProductAvgDTO() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }
}

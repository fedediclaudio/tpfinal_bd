package com.bd.tpfinal.DTOs;

public class SupplierDTO {

    public SupplierDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float[] getCoords() {
        return coords;
    }

    public void setCoords(float[] coords) {
        this.coords = coords;
    }

    public float getQualificationOfUsers() {
        return qualificationOfUsers;
    }

    public void setQualificationOfUsers(float qualificationOfUsers) {
        this.qualificationOfUsers = qualificationOfUsers;
    }


    public void setStarQualification(int  starQualification) {
        this.starQualification= starQualification;
    }

    public int getStarQualification() {
        return starQualification;
    }


    public void setCantQualifications(int  cantQualifications) {
        this.cantQualifications = cantQualifications;
    }

    public int getCantQualifications() {
        return cantQualifications;
    }


    private String name;
    private String cuil;
    private String address;
    private float[] coords;
    private float qualificationOfUsers; // promedio de las calificaciones totales
    private int starQualification; // para las busquedas por cantidad de estrellas
    private int cantQualifications; // cantidad de calificaciones al supplier
}

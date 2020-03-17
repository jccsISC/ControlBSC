package com.jccsisc.controlbsc.model;

public class Producto {
    String name, unit, status, camara, idKey, dateEntrada, dateSalida;
    float weight, quantity;

    public Producto() {}

    public Producto(String name, String unit, float weight, float quantity, String status, String camara, String idKey, String dateEntrada, String dateSalida) {
        this.name        = name;
        this.unit        = unit;
        this.weight      = weight;
        this.quantity    = quantity;
        this.status      = status;
        this.camara      = camara;
        this.idKey       = idKey;
        this.dateEntrada = dateEntrada;
        this.dateSalida  = dateSalida;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCamara() {
        return camara;
    }

    public void setCamara(String camara) {
        this.camara = camara;
    }

    public String getDateEntrada() {
        return dateEntrada;
    }

    public void setDateEntrada(String dateEntrada) {
        this.dateEntrada = dateEntrada;
    }

    public String getDateSalida() {
        return dateSalida;
    }

    public void setDateSalida(String dateSalida) {
        this.dateSalida = dateSalida;
    }

//
//    @Override
//    public String toString() {
//        return "Producto {" +
//                "name='" + name + '\'' +
//                ", unit='" + unit + '\'' +
//                ", status='" + status + '\'' +
//                ", camara='" + camara + '\'' +
//                ", sdKey='" + idKey + '\'' +
//                ", dateEntrada='" + dateEntrada + '\'' +
//                ", dateSalida='" + dateSalida + '\'' +
//                ", weight='" + weight + '\'' +
//                ", quantity=" +quantity +
//                '}';
//    }

}

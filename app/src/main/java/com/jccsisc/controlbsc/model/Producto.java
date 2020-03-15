package com.jccsisc.controlbsc.model;

public class Producto {
    private String name, unit, status, camara;
    private int weight, quantity;
    private String idKey;
    public Producto() {}

    public Producto(String name, String unit, int weight, int quantity, String status, String camara, String idKey) {
        this.name = name;
        this.unit = unit;
        this.weight = weight;
        this.quantity = quantity;
        this.status = status;
        this.camara = camara;
        this.idKey = idKey;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
}

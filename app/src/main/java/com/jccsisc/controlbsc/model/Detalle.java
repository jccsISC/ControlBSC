package com.jccsisc.controlbsc.model;

public class Detalle {

    public double peso;
    public String idKey;

    public Detalle(double peso, String idKey) {
        this.peso = peso;
        this.idKey = idKey;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }
}

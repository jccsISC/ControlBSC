package com.jccsisc.controlbsc.model;

public class Detalle {


    public String idKey;
    public double peso;

    public Detalle( String idKey, double peso) {
        this.idKey = idKey;
        this.peso = peso;

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

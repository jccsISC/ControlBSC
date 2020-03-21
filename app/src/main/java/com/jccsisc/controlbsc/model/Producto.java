package com.jccsisc.controlbsc.model;

import java.util.ArrayList;

public class Producto {

    public String name;
    public String unit;
    public String idKey;
    public ArrayList<Movimiento> movimientos = new ArrayList<>();


    public Producto(String name, String unit, String idKey) {
        this.name        = name;
        this.unit        = unit;
        this.idKey       = idKey;
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

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public ArrayList<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void addMovimiento(Movimiento movimiento){
        this.movimientos.add(movimiento);

    }

    public void setMovimientos(ArrayList<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

}

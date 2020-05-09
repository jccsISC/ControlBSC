package com.jccsisc.controlbsc.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Movimiento implements Serializable {

    public String date;
    public String type;
    public String hour;
    public String destiny;
    public String status;
    public String idKey;
    public double weight;
    public int quantity;
    public ArrayList<Detalle> detalles = new ArrayList<>();

    public Movimiento() {
    }

    public Movimiento(String date,
                      String type,
                      String hour,
                      String destiny,
                      String status,
                      String idKey,
                      double weight,
                      int quantity) {
        this.date     = date;
        this.type     = type;
        this.hour     = hour;
        this.destiny  = destiny;
        this.status   = status;
        this.idKey    = idKey;
        this.weight   = weight;
        this.quantity = quantity;
    }

    public void addDetalles(Detalle movimiento){
        this.detalles.add(movimiento);
    }

    public ArrayList<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<Detalle> movimientos) {
        this.detalles = movimientos;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

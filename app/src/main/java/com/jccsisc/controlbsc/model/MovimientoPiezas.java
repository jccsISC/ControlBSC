package com.jccsisc.controlbsc.model;


public class MovimientoPiezas {

    public String date;
    public String type;
    public String hour;
    public String destiny;
    public String status;
    public String idKey;
    public String idMovimiento;
    public double weight;
    public int quantity;

    public MovimientoPiezas() { }

    public MovimientoPiezas(String date,
                            String type,
                            String hour,
                            String destiny,
                            String status,
                            String idKey,
                            String idMovimiento,
                            double weight,
                            int quantity) {
        this.date     = date;
        this.type     = type;
        this.hour     = hour;
        this.destiny  = destiny;
        this.status   = status;
        this.idKey    = idKey;
        this.idMovimiento = idMovimiento;
        this.weight   = weight;
        this.quantity = quantity;
    }

    public String getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(String idMovimiento) {
        this.idMovimiento = idMovimiento;
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

package com.jccsisc.controlbsc.model;

import java.io.Serializable;

public class Proveedor implements Serializable {
    public String name, lastName, nameCompany, imgCompany, idKey, numberPhone;

    public Proveedor() { }

    public Proveedor(String name, String lastName, String nameCompany, String imgCompany, String numberPhone, String idKey) {
        this.name  = name;
        this.lastName = lastName;
        this.nameCompany = nameCompany;
        this.imgCompany = imgCompany;
        this.numberPhone = numberPhone;
        this.idKey = idKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getImgCompany() {
        return imgCompany;
    }

    public void setImgCompany(String imgCompany) {
        this.imgCompany = imgCompany;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
}

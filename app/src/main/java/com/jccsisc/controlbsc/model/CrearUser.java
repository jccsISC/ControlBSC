package com.jccsisc.controlbsc.model;

public class CrearUser {
    String idUser, imgUser, name, lastName, email, numberBodega;

    public CrearUser() {}

    public CrearUser(String idUser, String imgUser, String name, String lastName, String email, String numberBodega) {
        this.idUser   = idUser;
        this.imgUser  = imgUser;
        this.name     = name;
        this.lastName = lastName;
        this.email    = email;
        this.numberBodega = numberBodega;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberBodega() {
        return numberBodega;
    }

    public void setNumberBodega(String numberBodega) {
        this.numberBodega = numberBodega;
    }
}

package com.jccsisc.controlbsc.model;

public class CrearUser {
    String idUser, name, lastName, email;

    public CrearUser() {}

    public CrearUser(String idUser, String name, String lastName, String email) {
        this.idUser   = idUser;
        this.name     = name;
        this.lastName = lastName;
        this.email    = email;
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

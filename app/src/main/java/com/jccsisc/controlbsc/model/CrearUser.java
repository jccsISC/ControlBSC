package com.jccsisc.controlbsc.model;

public class CrearUser {
    String idUser, user;

    public CrearUser() {}

    public CrearUser(String idUser, String user) {
        this.idUser = idUser;
        this.user = user;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

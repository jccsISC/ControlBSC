package com.jccsisc.controlbsc.utilidades;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NodosFirebase {

    public static int tab = 0; //para controlar los tabs que no se destruyan
    public static String nameFragment = "Home";

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    public static Date date = new Date();

    public static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(NodosFirebase.DB_BODEGA_UNO).child(NodosFirebase.DB_PRODUCTOS);
    public static final String DB_BODEGA_UNO = "DB_Bodega1";
    public static final String DB_PRODUCTOS = "DB_Productos";

}

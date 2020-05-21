package com.jccsisc.controlbsc.utilidades;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jccsisc.controlbsc.dialogs.ChargingFragment;
import com.jccsisc.controlbsc.dialogs.ForgetPasswordFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NodosFirebase {

    public static int tab = 0; //para controlar los tabs que no se destruyan
    public static String addContac = ""; //cambiar icono del boton flotante

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    public static Date date = new Date();

    public static ChargingFragment chargingFragment = new ChargingFragment(); //mostrar el dialog cargando
    public static ForgetPasswordFragment forgetPasswordFragment = new ForgetPasswordFragment(); //mostrar el dialog olvidé password

    public static FirebaseAuth mAuth; // para conectarnos con el usuario de la db
    public static DatabaseReference myRefUser       = FirebaseDatabase.getInstance().getReference("DB_Users");
    public static StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("img_comprimidas");
    public static DatabaseReference myRef           = FirebaseDatabase.getInstance().getReference("DB_Bodega1").child("DB_Productos");
    public static DatabaseReference myRefProveedor  = FirebaseDatabase.getInstance().getReference("DB_Bodega1").child("DB_Proveedores");

}

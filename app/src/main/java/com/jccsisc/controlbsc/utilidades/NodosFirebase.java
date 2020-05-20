package com.jccsisc.controlbsc.utilidades;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jccsisc.controlbsc.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NodosFirebase {

    public static int tab = 0; //para controlar los tabs que no se destruyan
    public static String addContac = "";

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    public static Date date = new Date();

    public static DatabaseReference myRefUser = FirebaseDatabase.getInstance().getReference("DB_Users");
    public static DatabaseReference myRefProveedor = FirebaseDatabase.getInstance().getReference("DB_Bodega1").child("DB_Proveedores");
    public static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DB_Bodega1").child("DB_Productos");
    public static StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("img_comprimidas");

}

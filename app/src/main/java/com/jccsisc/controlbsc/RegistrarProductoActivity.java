package com.jccsisc.controlbsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jccsisc.controlbsc.model.Producto;

import java.util.ArrayList;

public class RegistrarProductoActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String PRODUCTO_NODE = "DB_Bodega1";
    private static final String DB_NODE = "DB_Productos";
    private DatabaseReference databaseReference, databaseReference2;

    private TextInputLayout tilProducto;
    private TextInputEditText tieRegistrarP;
    private String unit, status, camara;
    private Boolean miclick = false;
    private Button btnCaja, btnPieza, btnSave;
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarctivity);

        Toolbar toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.title_Registrar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//el boton de regresar
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference(); //obtenemos la db el nodo raiz controlbsc-899b5
        databaseReference2 = FirebaseDatabase.getInstance().getReference(PRODUCTO_NODE + "/" + DB_NODE);

        btnCaja     = findViewById(R.id.btnCaja);
        btnPieza    = findViewById(R.id.btnPieza);
        btnSave     = findViewById(R.id.btnSave);
        tilProducto = findViewById(R.id.tilRegistrarP);
        tieRegistrarP = findViewById(R.id.tieRegistrarP);


        tieRegistrarP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isProductEmpty(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        btnCaja.setOnClickListener(this);
        btnPieza.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCaja:
                unit    = "Caja";
                miclick=true;
                btnSelected("caja");
                if(unit.equals("Caja")) {
                    camara = "Congelación";
                }
                break;
            case R.id.btnPieza:
                unit    = "Pieza";
                miclick = true;
                btnSelected("pieza");
                    if(unit.equals("Pieza")) {
                        camara = "Conservación";
                    }
                break;
            case R.id.btnSave:
                    createProduct(v);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    //btnPulsado
    private void btnSelected( String boton) {
        if(boton.equals("caja")){
            btnCaja.setBackground(getDrawable(R.drawable.btnselected));
            btnCaja.setWidth((int) getResources().getDimension(R.dimen.width_btn_250));
            btnCaja.setHeight((int) getResources().getDimension(R.dimen.height_75));
//            btnCaja.setShadowLayer(5,10,10, getResources().getColor(R.color.shadow));

            btnPieza.setBackground(getDrawable(R.drawable.btn_no_selected));
            btnPieza.setWidth((int) getResources().getDimension(R.dimen.width_btn_250));
            btnPieza.setHeight((int) getResources().getDimension(R.dimen.height_75));
//            btnPieza.setShadowLayer(5,10,10, getResources().getColor(R.color.shadow));
        }else{
            btnPieza.setBackground(getDrawable(R.drawable.btnselected));
            btnPieza.setWidth((int) getResources().getDimension(R.dimen.width_btn_250));
            btnPieza.setHeight((int) getResources().getDimension(R.dimen.height_75));
//            btnPieza.setShadowLayer(5,10,10, getResources().getColor(R.color.shadow));

            btnCaja.setBackground(getDrawable(R.drawable.btn_no_selected));
            btnCaja.setWidth((int) getResources().getDimension(R.dimen.width_btn_250));
            btnCaja.setHeight((int) getResources().getDimension(R.dimen.height_75));
//            btnCaja.setShadowLayer(5,10,10, getResources().getColor(R.color.shadow));
        }
    }

    //crear productos
    private void createProduct(View v) {
        getProductos();
//        String nameProducto = tieRegistrarP.getEditableText().toString();
//
//        if (isProductEmpty(nameProducto)) {
//            if (miclick) {
//                String titulo = FirebaseDatabase.getInstance().getReference().push().getKey();
//                Producto producto = new Producto(nameProducto, unit, 0, 0, status, camara, titulo);
//
//                databaseReference.child(PRODUCTO_NODE).child("DB_Productos").child(titulo).setValue(producto); //se inserta el dato
//                mtoast(getString(R.string.registrado));
//                finish();
//            }else {
//                snackMessage(getString(R.string.unidad));
//            }
//        }
    }

    private void getProductos() {
        ArrayList<Producto> productitos = new ArrayList<>();


        final int iterador = 0;
        productitos.clear();
        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dt : dataSnapshot.getChildren()){
                    Log.e("elemento", dt.getValue(Producto.class).getName());
                    if(iterador == dataSnapshot.getChildrenCount()){
                        mtoast("final");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean isProductEmpty(CharSequence producto) {
        if(TextUtils.isEmpty(producto)) {
            productoMessage(getString(R.string.empty_field));
            return false;
        }
        productoMessage();
        return true;
    }

    public void snackMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    public void mtoast(String msj) {
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();
    }

    void productoMessage() {
        tilProducto.setError(null);
    }

    void productoMessage(String message) {
        tilProducto.setError(message);
    }

}

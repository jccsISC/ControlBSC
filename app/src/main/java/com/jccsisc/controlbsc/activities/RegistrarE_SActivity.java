package com.jccsisc.controlbsc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jccsisc.controlbsc.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegistrarE_SActivity extends AppCompatActivity {

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DB_Bodega1");
    private static final String DB_NODE = "DB_Entradas";
    private Button btnCargar;
    private TextView nameProducto, pesoTSin;
    private EditText  cajaTres, cajaDosCuatro, cajaDos, cajaUnoSeis, cajaUnoCuatro, cantPiezas, pesoTCon;

    private String dateEntrada, dateSalida = "", idKey, name;
    Intent extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_s_matanza);
        extras = getIntent();
        name  = extras.getStringExtra("nameProducto");
        idKey = extras.getStringExtra("idKey");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.entradas));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//el boton de regresar
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        dateEntrada = dateFormat.format(date);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        nameProducto = findViewById(R.id.txtNameProductoR);
        btnCargar    = findViewById(R.id.btnCargar);
        cajaTres     = findViewById(R.id.etdTreskg);
        cajaDosCuatro= findViewById(R.id.etdDosCuatrokg);
        cajaDos      = findViewById(R.id.edtDoskg);
        cajaUnoSeis  = findViewById(R.id.edtUnoSeiskg);
        cajaUnoCuatro= findViewById(R.id.etdUnoCuatrokg);
        cantPiezas   = findViewById(R.id.edtCantPiezas);
        pesoTCon     = findViewById(R.id.edtPesoTCajas);
        pesoTSin     = findViewById(R.id.txtPesoTSinC);

        nameProducto.setText(name);

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mtoast("estamos trabajando en esta vista");
                registrarEntradasPieza();
                finish();
            }
        });
    }


    //registrar las entradas de piezas
    private void registrarEntradasPieza() {
        RegistrarProductoActivity r = new RegistrarProductoActivity();
        String nameProduct = nameProducto.getText().toString();
        String unit = cantPiezas.getText().toString();
        String pesoTc = pesoTCon.getText().toString();
        String pesoTs = "25";
        pesoTSin.setText(pesoTs+"");

        Map<String, Object> entradaMap = new HashMap<>();
        entradaMap.put("unit", unit);

        myRef.child("DB_Entradas").child(idKey).updateChildren(entradaMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Los datos se han actualizado correctamente",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Ha ocurrido un error no se actualizaron los datos",Toast.LENGTH_SHORT).show();
            }
        });

    }

//    private boolean isProductEmpty(CharSequence producto) {
//        if(TextUtils.isEmpty(producto)) {
//            productoMessage(getString(R.string.empty_field));
//            return false;
//        }
//        productoMessage();
//        return true;
//    }

//    public void snackMessage(String message) {
//        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
//    }
//
    public void mtoast(String msj) {
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();
    }

//    void productoMessage() {
//        tilProducto.setError(null);
//    }
//
//    void productoMessage(String message) {
//        tilProducto.setError(message);
//    }

}

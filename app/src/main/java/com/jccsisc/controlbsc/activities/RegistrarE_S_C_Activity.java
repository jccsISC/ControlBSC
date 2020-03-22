package com.jccsisc.controlbsc.activities;

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
import com.jccsisc.controlbsc.VariablesGLobales;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegistrarE_S_C_Activity extends AppCompatActivity {

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DB_Bodega1");
    private static final String DB_NODE = "DB_Entradas";
    private Button btnCargar, btnSumar;
    private TextView nameProducto, txtCT, txtPesoT;
    private EditText  txtPesoC;

    private String dateEntrada, dateSalida = "", idKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_s_matanza);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.entradas));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//el boton de regresar
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        dateEntrada = dateFormat.format(date);



        nameProducto = findViewById(R.id.txtNameProductoR);
        txtCT        = findViewById(R.id.textNameProducto);
        txtPesoT     = findViewById(R.id.txtPesoT);
        btnCargar    = findViewById(R.id.btnCargar);
        btnSumar     = findViewById(R.id.btnSumar);

        nameProducto.setText(VariablesGLobales.nombreProducto);
        idKey = VariablesGLobales.idKeyProducto;

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mtoast("estamos trabajando en esta vista");
                registrarEntradasCaja();
            }
        });
    }


    //registrar las entradas de piezas
    private void registrarEntradasCaja() {

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

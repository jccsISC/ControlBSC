package com.jccsisc.controlbsc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jccsisc.controlbsc.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegistrarE_SActivity extends AppCompatActivity {

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DB_Bodega1").child("DB_Productos");
//    private static final String DB_NODE = "DB_Entradas";
    private Button btnCargar;
    private TextView nameProducto, pesoTSin;
    private EditText cantPiezas, pesoTCon;
    private EditText[] txtCajas = new EditText[6];
    double[] pesosCajas =  {3,2.4,2.2,2,1.6,1.4};
    private String dateEntrada, dateSalida = "", idKey, name;
    private int hora, minutos, segundos;
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date   = new Date();
        dateEntrada = dateFormat.format(date);

        Calendar calendario = new GregorianCalendar();
        hora    = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos= calendario.get(Calendar.SECOND);
        String horaEntrada = hora + " : " + minutos + " : " + segundos;

        nameProducto = findViewById(R.id.txtNameProductoR);
        btnCargar    = findViewById(R.id.btnCargar);
        txtCajas[0]  = findViewById(R.id.etdNumThree);
        txtCajas[1]  = findViewById(R.id.etdNumTwoFour);
        txtCajas[2]  = findViewById(R.id.etdNumTwoTwo);
        txtCajas[3]  = findViewById(R.id.etdNumTwo);
        txtCajas[4]  = findViewById(R.id.etdNumOneSix);
        txtCajas[5]  = findViewById(R.id.etdNumOneFour);
        cantPiezas   = findViewById(R.id.edtCantPiezas);
        pesoTCon     = findViewById(R.id.edtPesoTCajas);
        pesoTSin     = findViewById(R.id.txtPesoTSinC);

        nameProducto.setText(name);
        pesoTCon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count != 0 ) {
                    calcular(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarEntradasPieza();
                finish();
            }
        });
    }


    private void calcular(CharSequence s) {
        double pesoTotalConCaja = Double.parseDouble(s.toString()); //30
        double pesoCajasVacias = 0.0;
        double pesoFinal = 0.0;

        for(int x = 0; x < pesosCajas.length; x++){

            if(!txtCajas[x].getText().toString().equals("")) {
                pesoCajasVacias += Double.parseDouble(txtCajas[x].getText().toString()) * pesosCajas[x];
            }
        }
        pesoFinal += pesoTotalConCaja - pesoCajasVacias;
        pesoFinal = Math.rint(pesoFinal * 100)/100;
        pesoTSin.setText(String.valueOf(pesoFinal));
        pesoTSin.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    //registrar las entradas de piezas
    private void registrarEntradasPieza() {
        String cantidadPz = cantPiezas.getText().toString();
        if (quantityEmpty(cantidadPz)) {
           mtoast(cantidadPz);
       }
    }

    private boolean quantityEmpty(String cantidad) {
        if (!TextUtils.isEmpty(cantidad)) {
            cantidadMessage();
            return true;
        } else {
            cantidadMessage(getString(R.string.empty_field));
            return false;
        }
    }

    public void snackMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    public void mtoast(String msj) {
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();
    }

    void cantidadMessage() {
        cantPiezas.setError(null);
    }

    void cantidadMessage(String message) {
        cantPiezas.setError(message);
    }

}

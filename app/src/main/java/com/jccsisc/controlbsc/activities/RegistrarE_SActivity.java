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
import com.jccsisc.controlbsc.dialogs.ChargingFragment;
import com.jccsisc.controlbsc.model.Detalle;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.model.MovimientoPiezas;
import com.jccsisc.controlbsc.utilidades.NodosFirebase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegistrarE_SActivity extends AppCompatActivity {

    public static int hora, minutos, segundos;
    Intent extras;
    public double pesoFinal = 0.0;
    private Button btnCargar;
    private TextView nameProducto, pesoTSin;
    private EditText cantPiezas, pesoTCon;
    private EditText[] txtCajas = new EditText[6];
    double[] pesosCajas =  {3,2.4,2.2,2,1.5,1.4};
    public static String horaEntrada, dateEntrada, dateSalida = "", idKey, name;
    ChargingFragment chargingFragment = new ChargingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_s_matanza);

        extras = getIntent();
        idKey = extras.getStringExtra("idKey");
        name  = extras.getStringExtra("nameProducto");

        toolbar();
        obtenerFecha();
        obtenerHora();

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
                if (count != 0) {
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
                if (!pesoTCon.getText().toString().trim().equals("")) {
                    registrarEntradasPieza();
                }else {
                    mtoast("Ingresa la cantidad");
                    pesoTCon.setHintTextColor(getResources().getColor(R.color.colorPieza));
                    pesoTCon.setBackground(getResources().getDrawable(R.drawable.borde_card_red));
                }

            }
        });
    }


    private void toolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.registrar_entradas));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//el boton de regresar
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void obtenerFecha() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date   = new Date();
        dateEntrada = dateFormat.format(date);
    }

    private void obtenerHora() {
        Calendar calendario = new GregorianCalendar();
        hora    = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos= calendario.get(Calendar.SECOND);
        horaEntrada = hora + " : " + minutos + " : " + segundos;
    }

    private void calcular(CharSequence s) {
        double pesoTotalConCaja = Double.parseDouble(s.toString());
        double pesoCajasVacias = 0.0;
        pesoFinal = 0.0;

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
        String id = FirebaseDatabase.getInstance().getReference().push().getKey();

        chargingFragment.show(getSupportFragmentManager(), "dialogChargin");

        int cantPieza = 0;

        if (cantPiezas.getText().toString().equals("")) {
            cantPieza = 0;
        }else {
            cantPieza = Integer.parseInt(cantPiezas.getText().toString());
        }

        MovimientoPiezas movimientoCarne = new MovimientoPiezas(dateEntrada, "positive", horaEntrada, "Conservacion",
                "normal", id, pesoFinal, cantPieza);

        NodosFirebase.myRef.child(idKey).child("movimientos").child(id).setValue(movimientoCarne)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mtoast("se cuardó correctamente");
                        finish();
                    }
                });
    }

    private boolean quantityEmpty(String cantidad) {
        if (TextUtils.isEmpty(cantidad)) {
            pesoTCon.setHintTextColor(getResources().getColor(R.color.colorPrimary));
            return false;
        } else {

            return true;
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

}

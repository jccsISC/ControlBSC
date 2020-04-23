package com.jccsisc.controlbsc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.adapters.CajasAdapter;
import com.jccsisc.controlbsc.dialogs.ForgetPasswordFragment;
import com.jccsisc.controlbsc.dialogs.ModifyCajaFragment;
import com.jccsisc.controlbsc.model.Detalle;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.utilidades.NodosFirebase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class RegistrarE_S_C_Activity extends AppCompatActivity {

    Intent extras;
    double sumatotal = 0.0;
    private Button btnCargar;
    private EditText edtPesoC;
    private ImageButton  btnSumar;
    private CajasAdapter cajasAdapter;
    public static String horaE_S, dateEntrada;
    public static int hora, minutos, segundos;
    private RecyclerView recyclerPesadasC;
    private TextView nameProducto, txtCT, txtPesoT;
    private String  dateSalida = "", idKey, name;
    private ArrayList<Detalle> detallesArrayList = new ArrayList<>();
    private ModifyCajaFragment modifyCajaFragment = new ModifyCajaFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_s_productos_caja);

        toolbar();
        obtenerFecha();
        obtenerHora();

        extras = getIntent();
        name  = extras.getStringExtra("nameProducto");
        idKey = extras.getStringExtra("idKey");

        recyclerPesadasC = findViewById(R.id.recyclerPesadasC);
        nameProducto     = findViewById(R.id.txtNameProductoR);
        txtCT            = findViewById(R.id.txtCT);
        txtPesoT         = findViewById(R.id.txtPesoT);
        btnCargar        = findViewById(R.id.btnCargar);
        btnSumar         = findViewById(R.id.btnSumar);
        edtPesoC         = findViewById(R.id.edtPesoC);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerPesadasC.setLayoutManager(linearLayoutManager);

        cajasAdapter = new CajasAdapter(detallesArrayList, this);

        recyclerPesadasC.setAdapter(cajasAdapter);

        btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPesoC.getText().toString().trim().equals("")){
                    mtoast("Ingrese una cantidad");
                }else{
                    double peso = Double.parseDouble(edtPesoC.getText().toString().trim());
                    sumatotal += peso;
                    sumatotal = Math.rint(sumatotal * 100)/100;
                    String id =  FirebaseDatabase.getInstance().getReference().push().getKey();
                    Detalle modelitoDetalle =  new Detalle(id, peso);
                    modelitoDetalle.setIdKey(id);

                    detallesArrayList.add(modelitoDetalle);
                    cajasAdapter.notifyDataSetChanged();
                    edtPesoC.setText("");
                    txtCT.setText(String.valueOf(detallesArrayList.size()));
                    txtPesoT.setText(String.valueOf(sumatotal));
                }
            }
        });

        nameProducto.setText(name);

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detallesArrayList.size() == 0){
                    mtoast("No hay elementos");
                }else{
                    String id = FirebaseDatabase.getInstance().getReference().push().getKey();

                    Movimiento movimiento = new Movimiento(dateEntrada,"positive",horaE_S, "Congelacion",
                            "normal",id,sumatotal,detallesArrayList.size());

                movimiento.setDetalles(detallesArrayList);

                    NodosFirebase.myRef.child(idKey).child("movimientos").child(id).setValue(movimiento)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mtoast("Exito");
                            detallesArrayList.clear();
                            cajasAdapter.notifyDataSetChanged();
                            sumatotal = 0.0;
                            txtPesoT.setText("");
                            txtCT.setText("");
                            finish();
                        }
                    });
                }
            }
        });

        cajasAdapter.setOnClickListener(new CajasAdapter.OnClickListener() {
            @Override
            public void onItemClick(final int pos) {
                modifyCajaFragment.show(getSupportFragmentManager(), "dialogModifyCaja");

            }
        });

    }//onCreate

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

    public static void obtenerFecha() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        dateEntrada = dateFormat.format(date);
    }

    public static void obtenerHora() {
        Calendar calendario = new GregorianCalendar();
        hora    = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos= calendario.get(Calendar.SECOND);
        horaE_S = hora + ":" + minutos + ":" + segundos;
    }

    public void mtoast(String msj) {
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();
    }

}

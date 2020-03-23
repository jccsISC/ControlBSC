package com.jccsisc.controlbsc.activities;

import android.content.Intent;
import android.media.midi.MidiOutputPort;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jccsisc.controlbsc.R;
import com.jccsisc.controlbsc.adapters.CajasAdapter;
import com.jccsisc.controlbsc.adapters.ProductosAdapter;
import com.jccsisc.controlbsc.model.Detalle;
import com.jccsisc.controlbsc.model.Movimiento;
import com.jccsisc.controlbsc.model.Producto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RegistrarE_S_C_Activity extends AppCompatActivity {

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DB_Bodega1").child("DB_Productos");
    private static final String DB_NODE = "DB_Entradas";
    private Button btnCargar, btnSumar;
    private TextView nameProducto, txtCT, txtPesoT;
    private EditText edtPesoC;
    private String dateEntrada, dateSalida = "", idKey, name;
    Intent extras;
    double sumatotal = 0.0;
    private ArrayList<Detalle> detallesArrayList = new ArrayList<>();
    private CajasAdapter cajasAdapter;
    RecyclerView recyclerPesadasC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_s_productos_caja);

        extras = getIntent();
        name  = extras.getStringExtra("nameProducto");
        idKey = extras.getStringExtra("idKey");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        dateEntrada = dateFormat.format(date);


        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerPesadasC = findViewById(R.id.recyclerPesadasC);
        nameProducto = findViewById(R.id.txtNameProductoR);
        txtCT        = findViewById(R.id.txtCT);
        txtPesoT     = findViewById(R.id.txtPesoT);
        btnCargar    = findViewById(R.id.btnCargar);
        btnSumar     = findViewById(R.id.btnSumar);
        edtPesoC     = findViewById(R.id.edtPesoC);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerPesadasC.setLayoutManager(linearLayoutManager);

        cajasAdapter = new CajasAdapter(detallesArrayList, this);

        recyclerPesadasC.setAdapter(cajasAdapter);

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

        btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPesoC.getText().toString().trim().equals("")){
                    mtoast("Ingrese una cantidad");
                }else{
                    double peso = Double.parseDouble(edtPesoC.getText().toString());
                    sumatotal += peso;
                    String id =  FirebaseDatabase.getInstance().getReference().push().getKey();
                    Detalle modelitoDetalle =  new Detalle(id, peso);
                    modelitoDetalle.setIdKey(id);
                    detallesArrayList.add(modelitoDetalle);
                    cajasAdapter.notifyDataSetChanged();
                    edtPesoC.setText("");
                    txtPesoT.setText(String.valueOf(sumatotal));
                    txtCT.setText(String.valueOf(detallesArrayList.size()));
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

                    Movimiento movimiento = new Movimiento(dateEntrada,"positive","15:15:00", "Congelacion",
                            "normal",id,sumatotal,detallesArrayList.size());

                movimiento.setDetalles(detallesArrayList);

                    myRef.child(idKey).child("movimientos").child(id).setValue(movimiento).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mtoast("Exito");
                            detallesArrayList.clear();
                            cajasAdapter.notifyDataSetChanged();
                            sumatotal = 0.0;
                            txtPesoT.setText("");
                            txtCT.setText("");
                        }
                    });
                }

            }
        });

    }//onCreate


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
//        edtPesoC.setError(null);
//    }
//
//    void productoMessage(String message) {
//        edtPesoC.setError(message);
//    }

}

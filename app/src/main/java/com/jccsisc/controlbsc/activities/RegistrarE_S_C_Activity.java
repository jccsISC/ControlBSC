package com.jccsisc.controlbsc.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RegistrarE_S_C_Activity extends AppCompatActivity {

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DB_Bodega1").child("DB_Productos");
    private static final String DB_NODE = "DB_Entradas";
    private Button btnCargar, btnSumar, btnAceptar, btnCancel;
    private TextView nameProducto, txtCT, txtPesoT;
    private EditText edtPesoC, edtEditarPeso;
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

                    double peso = Double.parseDouble(edtPesoC.getText().toString().trim());
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


        cajasAdapter.setOnClickListener(new CajasAdapter.OnClickListener() {
            @Override
            public void onItemClick(final int pos) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegistrarE_S_C_Activity.this);
                alertDialog.setTitle("MODIFICAR");
                alertDialog.setMessage(String.valueOf(detallesArrayList.get(pos).getPeso()));

                final EditText input = new EditText(RegistrarE_S_C_Activity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Guardar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String idKey = detallesArrayList.get(pos).getIdKey();
                                Detalle detalle = new Detalle(idKey, Double.parseDouble(input.getText().toString().trim()));
                                detallesArrayList.remove(pos);
                                detallesArrayList.add(pos, detalle);
                                cajasAdapter.notifyItemChanged(pos);
                                sumatotal = 0.0;
                                for(int x = 0; x < detallesArrayList.size(); x++){

                                    sumatotal += detallesArrayList.get(x).getPeso();
                                    txtPesoT.setText(String.valueOf(sumatotal));
                                }

                            }
                        });

                alertDialog.setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });

    }//onCreate

    public void mtoast(String msj) {
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();
    }

}
